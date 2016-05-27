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
import android.widget.TextView;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.modules.Alcohol;

import java.text.DecimalFormat;

/**
 *
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class AlcoholFragment extends Fragment {


    private View mView;
    private TextView textView;

    private boolean threadOn = false;

    private Alcohol alcohol;

    private AlcoholReadThread alcoholReadThread = null;

    private final String ALOCHOL = "ALOCHOL";

    static{
        System.loadLibrary("operate");
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String value = msg.getData().getString(ALOCHOL);
                    textView.setTextSize(50);
                    textView.setText(String.format("%sV", value));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alcohol = Alcohol.getAlcohol();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.alcohol_fragment, container, false);
        initShow();

        alcoholReadThread = new AlcoholReadThread();
        threadOn = true;
        alcoholReadThread.start();

        return mView;
    }

    private void initShow() {
        textView = (TextView) mView.findViewById(R.id.alcohol);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadOn = false;
        alcoholReadThread.interrupt();
    }

    private class AlcoholReadThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (threadOn){

                Bundle b = new Bundle();
                Message msg = new Message();
                DecimalFormat df = new DecimalFormat("#.##");

                int value = alcohol.operate.read()[0];
                String result = df.format((double)value*3.3/4096);

                b.putString(ALOCHOL, result);
                msg.what = 1;
                msg.setData(b);
                handler.sendMessage(msg);

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
