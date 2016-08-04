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
                    textView.setTextSize(25);
                    textView.setText(String.format("%s", value));
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

                int value = alcohol.operate.read()[0];
                @SuppressLint("DefaultLocale")
                String result = String.format("酒精：%.2f ppm", getValue(value));

                b.putString(ALOCHOL, result);
                msg.what = 1;
                msg.setData(b);
                handler.sendMessage(msg);

                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private double getValue(int datas) {
        double result;
        switch (datas / 455) {
            case 0:
            case 1:
            case 2:
            case 3:
                result = (36.00 * datas) / (455 * 4);
                break;
            case 4:
                result = 36 + (34.00 * (datas % 455)) / 455;
                break;
            case 5:
                result = 70 + (30.00 * (datas % 455)) / 455;
                break;
            case 6:
                result = 100 + (45.00 * (datas % 455)) / 455;
                break;
            case 7:
                if (datas % 455 < 273) {
                    result = 145 + (55.00 * (datas % 455)) / 455;
                } else {
                    result = 200 + (100.00 * (datas % 455)) / 455;
                }
                break;
            case 8:
                if (datas % 455 < 220) {
                    result = 300 + (200.00 * (datas % 455)) / 455;
                } else {
                    result = 501.00;
                }
                break;
            default:
                result = 501.00;
                break;
        }

        return result;
    }
}
