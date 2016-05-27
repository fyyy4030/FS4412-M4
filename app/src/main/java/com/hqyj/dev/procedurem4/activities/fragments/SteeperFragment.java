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
import com.hqyj.dev.procedurem4.modules.modules.Steeper;

import view.DrawSteeperButton;

/**
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class SteeperFragment extends Fragment {

    private View mView;

    private Steeper steeper;

    private DrawSteeperButton drawSteeperButton;
    private TextView textView;
    private String TAG = "STEEPER";
    private boolean threadOn = false;
    private int steeperSpeed = 0;
    private SteeperWriteThread steeperWriteThread = null;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @SuppressLint("DefaultLocale")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int speed = msg.getData().getInt(TAG);
                    textView.setText(String.format("速度：%d 档", speed));
                    steeperSpeed = speed;
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
        steeper = Steeper.getSteeper();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.steeper_fragment, container, false);

        initShow();

        threadOn = true;
        steeperWriteThread = new SteeperWriteThread();
        steeperWriteThread.start();

        drawSteeperButton.setOnSpeedChanage(new DrawSteeperButton.OnSpeedChanage() {
            @Override
            public void speed(int speed) {
                Bundle b = new Bundle();
                b.putInt(TAG, speed);
                Message msg = new Message();
                msg.what = 1;
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });

        drawSteeperButton.setState(0);


        return mView;
    }

    private void initShow() {
        drawSteeperButton = (DrawSteeperButton) mView.findViewById(R.id.draw_steeper);
        textView = (TextView) mView.findViewById(R.id.txv_steeper);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadOn = false;
        steeperWriteThread.interrupt();
    }

    private class SteeperWriteThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (threadOn){
                steeper.operate.write(steeperSpeed);
                if (steeperSpeed == 0){
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
