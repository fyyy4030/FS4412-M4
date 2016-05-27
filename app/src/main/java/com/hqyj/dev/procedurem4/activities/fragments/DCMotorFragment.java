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
import com.hqyj.dev.procedurem4.modules.modules.DCMotor;

import view.DrawDCButton;

/**
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class DCMotorFragment extends Fragment {

    private View mView;

    private DCMotor dcMotor;
    private final String TAG = "DC_MOTOR";

    private DrawDCButton dcButton;

    private TextView textView;

    static {
        System.loadLibrary("operate");
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int which = msg.getData().getInt(TAG);
//                    Log.d(TAG,""+which);
                    dcMotor.operate.write(which);

                    switch (which){
                        case 0:
                            textView.setText(String.format("%s按钮被按下","关闭"));
                            break;
                        case 1:
                            textView.setText(String.format("%s按钮被按下","正转"));
                            break;
                        case 2:
                            textView.setText(String.format("%s按钮被按下","反转"));
                            break;
                        default:
                            break;
                    }

                    break;
            }
        }
    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dcMotor = DCMotor.getDcMotor();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dc_motor_fragment, container, false);
        initShow();

        dcMotor.operate.init();
        dcButton.setState(0);
        dcButton.invalidate();
        dcButton.setOnButtonClicked(new DrawDCButton.OnButtonClicked() {
            @Override
            public void onclicked(int which) {
                Bundle b = new Bundle();
                b.putInt(TAG, which);
                Message msg = new Message();
                msg.what = 1;
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        return mView;
    }

    private void initShow() {
        dcButton = (DrawDCButton) mView.findViewById(R.id.dc);
        textView = (TextView) mView.findViewById(R.id.txv_dc);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dcButton.setState(3);
        dcButton.invalidate();
        dcMotor.operate.write(3);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dcMotor.operate.write(3);
    }
}
