package com.hqyj.dev.procedurem4.activities.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.modules.Brake;

/**
 * 光电闸
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class BrakeFragment extends Fragment {
    private View mView;
    private TextView textView;
    private TextView textViewCount;
    private boolean threadOn = false;
    private BrakeReadThread brakeReadThread;
    private Brake brake;

    private int count = 0;

    private String TAG = "BRAKE";

    private int oldState = -1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("DefaultLocale")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    int state = msg.getData().getInt(TAG);
                    textView.setTextSize(30);
                    textView.setText(String.format("光电闸：%s", (state == 0x00) ? "开" : (state == 0x10) ? "关" : "未知"));
                    textViewCount.setText(String.format("光电闸开关次数：%d", count));
                    break;
            }
        }
    };

    static {
        System.loadLibrary("operate");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brake = Brake.getBrake();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.brake_fragment, container, false);

        initShow();
        threadOn = true;
        brakeReadThread = new BrakeReadThread();
        brakeReadThread.start();

        return mView;
    }

    private void initShow() {
        textView = (TextView) mView.findViewById(R.id.txv_brake);
        textViewCount = (TextView) mView.findViewById(R.id.txv_count_brake);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadOn = false;
        brakeReadThread.interrupt();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        threadOn = false;
        brakeReadThread.interrupt();
        count = 0;
        oldState = -1;
    }

    private class BrakeReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn) {
                int value = brake.operate.read()[0];


                if (value != oldState) {

                    Bundle bundle = new Bundle();
                    bundle.putInt(TAG, value);

                    Message msg = new Message();
                    msg.what = 1;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    oldState = value;
                    if (value == 0)
                        count ++;
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
