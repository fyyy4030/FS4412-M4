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
import com.hqyj.dev.procedurem4.modules.modules.Gas;

import java.text.DecimalFormat;

/**
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class GasFragment extends Fragment {
    private View mView;

    private GasReadThread gasReadThread;
    private boolean threadOn = false;

    private TextView textView;
    private Gas gas;

    private String TAG = "GAS";

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
        gas = Gas.getGas();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.gas_fragment, container, false);

        initShow();

        threadOn = true;
        gasReadThread = new GasReadThread();
        gasReadThread.start();

        return mView;
    }

    private void initShow() {
        textView = (TextView) mView.findViewById(R.id.txv_gas);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadOn = false;
        gasReadThread.interrupt();
    }

    private class GasReadThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (threadOn) {
                Bundle b = new Bundle();
                Message msg = new Message();
                DecimalFormat df = new DecimalFormat("#.##");

                int value = gas.operate.read()[0];
                String result = df.format((double) value * 7.2 / 4096);

                b.putString(TAG, result);
                Log.d(TAG, result);
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
