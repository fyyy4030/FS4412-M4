package com.hqyj.dev.procedurem4.activities.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.modules.Buzzer;

/**
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class BuzzerFragment extends Fragment implements View.OnClickListener {

    private View mView;

    private WriteBuzzerThread writeBuzzerThread;
    private boolean threadOn = false;

    private Buzzer buzzer;

    private int num = 80;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buzzer = Buzzer.getBuzzer();
    }

    static {
        System.loadLibrary("operate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.buzzer_fragment, container, false);

        initShow();

        mView.findViewById(R.id.btn_open).setOnClickListener(this);
        mView.findViewById(R.id.btn_close).setOnClickListener(this);

        return mView;
    }

    private void initShow() {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        threadOn = false;
        writeBuzzerThread = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadOn = false;
        writeBuzzerThread = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open:
                if (writeBuzzerThread == null) {
                    threadOn = true;
                    num = 80;
                    writeBuzzerThread = new WriteBuzzerThread();
                    writeBuzzerThread.start();
                }
                break;
            case R.id.btn_close:
                if (writeBuzzerThread != null) {
                    num = 0;
                    threadOn = false;
                    writeBuzzerThread.interrupt();
                    writeBuzzerThread = null;
                }
                buzzer.operate.write(0);
                break;
            default:
                break;
        }
    }


    private class WriteBuzzerThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn) {
                buzzer.operate.write(num);
            }
        }
    }
}
