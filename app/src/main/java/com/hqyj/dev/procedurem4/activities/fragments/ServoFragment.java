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
import android.widget.RadioGroup;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.modules.Servo;

import view.DrawServo;

/**
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class ServoFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private View mView;
    private DrawServo drawServo;

    private Servo servo;
    private String TAG = "SERVO";

//    private boolean threadOn = false;
//    private ServoWriteThread servoWriteThread;
    private int degree = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
//                    Log.d(TAG, msg.getData().getInt(TAG) +"角度");
                    degree = msg.getData().getInt(TAG);
                    servo.operate.write(degree);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        servo = Servo.getServo();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.servo_fragment, container, false);

        initShow();

        drawServo.setOnDegreeChanged(new DrawServo.OnDegreeChanged() {
            @Override
            public void onDegreeChanged(int degree) {
                Bundle bundle = new Bundle();
                bundle.putInt(TAG, degree);
                Message msg = new Message();
                msg.what = 1;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        ((RadioGroup) mView.findViewById(R.id.radio_group)).setOnCheckedChangeListener(ServoFragment.this);

//        servoWriteThread = new ServoWriteThread();
//        threadOn = true;
//        servoWriteThread.start();
        return mView;
    }

    private void initShow() {
        drawServo = (DrawServo) mView.findViewById(R.id.draw_servo);
        drawServo.setDegree(90);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int degree = 0;
        switch (group.getId()){
            case R.id.radio_group:
                switch (checkedId){
                    case R.id.radio_1:
                        degree = 30;
                        break;
                    case R.id.radio_2:
                        degree = 60;
                        break;
                    case R.id.radio_3:
                        degree = 90;
                        break;
                    case R.id.radio_4:
                        degree = 120;
                        break;
                    case R.id.radio_5:
                        degree = 150;
                        break;

                    default:
                        break;
                }
                break;
        }
        drawServo.setDegree(degree);
        drawServo.invalidate();
    }


//    private class ServoWriteThread extends Thread{
//        @Override
//        public void run() {
//            super.run();
//            while(threadOn){
//                servo.operate.write(degree);
//            }
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        threadOn = false;
        servo.operate.write(-1);
//        servoWriteThread.interrupt();
    }
}
