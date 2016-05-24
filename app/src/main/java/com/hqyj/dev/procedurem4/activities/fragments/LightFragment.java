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
import com.hqyj.dev.procedurem4.modules.modules.Light;

import java.text.DecimalFormat;

/**
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class LightFragment extends Fragment {

    private View mView;
    private boolean threadOn = false;
    private LightReadThread lightReadThread;

    private TextView txvLight;

    private Light light;

    private final String TAG = "LIGHT";

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
                    String value = msg.getData().getString(TAG);
                    txvLight.setTextSize(50);
                    txvLight.setText(String.format("%sV", value));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        light = Light.getLight();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.light_fragment, container, false);

        initShow();

        lightReadThread = new LightReadThread();
        threadOn = true;
        lightReadThread.start();
        return mView;
    }

    private void initShow() {
        txvLight = (TextView) mView.findViewById(R.id.txv_light);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadOn = false;
        lightReadThread.interrupt();
    }

    private class LightReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn){

                Bundle b = new Bundle();
                Message msg = new Message();
                DecimalFormat df = new DecimalFormat("#.##");

                int value = light.operate.read()[0];
                String result = df.format((double)value*7.2/4096);

                b.putString(TAG, result);
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
