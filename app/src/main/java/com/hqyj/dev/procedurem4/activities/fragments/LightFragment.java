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

/**
 *
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
                    txvLight.setTextSize(25);
                    txvLight.setText(String.format("%s", value));
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

                int value = light.operate.read()[0];
                @SuppressLint("DefaultLocale")
                String result = String.format("光强 %.2f Lux", getlux(value));

                b.putString(TAG, result);
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

    private double getlux(int datas) {
        double result;
        int oumig = (datas * 1000) / (4096 - datas);
        if (oumig < 1500) {
            result = 0.00;
        } else if (oumig < 1557) {
            result = 100.00 - (double) (oumig - 1500) * 10.00 / 57.00;
        } else if (oumig < 1657) {
            result = 90.00 - (double) (oumig - 1557) * 10.00 / 100.00;
        } else if (oumig < 1757) {
            result = 80.00 - (double) (oumig - 1657) * 10.00 / 100.00;
        } else if (oumig < 1885) {
            result = 70.00 - (double) (oumig - 1757) * 10.00 / 128.00;
        } else if (oumig < 2000) {
            result = 60.00 - (double) (oumig - 1885) * 10.00 / 115.00;
        } else if (oumig < 2333) {
            result = 50.00 - (double) (oumig - 2000) * 10.00 / 333.00;
        } else if (oumig < 2738) {
            result = 40.00 - (double) (oumig - 2333) * 10.00 / 405.00;
        } else if (oumig < 3555) {
            result = 30 - (double) (oumig - 2738) * 10.00 / 817.00;
        } else if (oumig < 5150) {
            result = 20 - (double) (oumig - 3555) * 10.00 / 1595.00;
        }else if (oumig < 5500) {
            result = 10 - (double) (oumig - 5150) * 1.00 / 350.00;
        }else if (oumig < 6000) {
            result = 9 - (double) (oumig - 5500) * 1.00 / 500.00;
        }else if (oumig < 6340) {
            result = 8 - (double) (oumig - 6000) * 1.00 / 340.00;
        }else if (oumig < 7000) {
            result = 7 - (double) (oumig - 6340) * 1.00 / 660.00;
        } else if (oumig < 7800) {
            result = 6 - (double) (oumig - 7000) * 1.00 / 800.00;
        }else if (oumig < 9000) {
            result = 5 - (double) (oumig - 7800) * 1.00 / 1200.00;
        }else if (oumig < 11000) {
            result = 4 - (double) (oumig - 9000) * 1.00 / 2000.00;
        }else if (oumig < 14000) {
            result = 3 - (double) (oumig - 11000) * 1.00 / 3000.00;
        }else if (oumig < 20000) {
            result = 2 - (double) (oumig - 14000) * 1.00 / 6000.00;
        }else {
            result = -1;
        }
        return result;
    }
}
