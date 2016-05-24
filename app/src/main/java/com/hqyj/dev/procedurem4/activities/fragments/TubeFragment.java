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
import android.widget.EditText;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.modules.Tube;

import java.util.Calendar;

/**
 * 数码管
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class TubeFragment extends Fragment implements View.OnClickListener {

    private View mView;

    private String TAG = "TUBE";
    private Tube tube;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    tube.operate.write(msg.getData().getInt(TAG));
                    break;
                default:
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
        tube = Tube.getTube();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.tube_fragment, container, false);

        initShow();
        mView.findViewById(R.id.btn_tube_submit).setOnClickListener(this);
        mView.findViewById(R.id.btn_tube_system_time).setOnClickListener(this);
        mView.findViewById(R.id.btn_0).setOnClickListener(this);
        mView.findViewById(R.id.btn_1).setOnClickListener(this);
        mView.findViewById(R.id.btn_2).setOnClickListener(this);
        mView.findViewById(R.id.btn_3).setOnClickListener(this);
        mView.findViewById(R.id.btn_4).setOnClickListener(this);
        mView.findViewById(R.id.btn_5).setOnClickListener(this);
        mView.findViewById(R.id.btn_6).setOnClickListener(this);
        mView.findViewById(R.id.btn_7).setOnClickListener(this);
        mView.findViewById(R.id.btn_8).setOnClickListener(this);
        mView.findViewById(R.id.btn_9).setOnClickListener(this);

        return mView;
    }

    private void initShow() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    @SuppressLint("DefaultLocale")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tube_submit:
                String valeu = "123 5678";
                setCMD(valeu);
                break;
            case R.id.btn_tube_system_time:
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);
                String valueT =
                        String .format("%02d %02d %02d", hour, minute, second);
                setCMD(valueT);
                break;
            case R.id.btn_0:
                setStringShow(0);
                break;
            case R.id.btn_1:
                setStringShow(1);
                break;
            case R.id.btn_2:
                setStringShow(2);
                break;
            case R.id.btn_3:
                setStringShow(3);
                break;
            case R.id.btn_4:
                setStringShow(4);
                break;
            case R.id.btn_5:
                setStringShow(5);
                break;
            case R.id.btn_6:
                setStringShow(6);
                break;
            case R.id.btn_7:
                setStringShow(7);
                break;
            case R.id.btn_8:
                setStringShow(8);
                break;
            case R.id.btn_9:
                setStringShow(9);
                break;


        }
    }

    void setCMD(String value){
        int operate = 0;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == ' ') {
                operate = operate << 4 | (0x0f);
            } else {
                operate = operate << 4 | ((value.charAt(i) - '0') & 0x0f);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, operate);
        Message msg = new Message();
        msg.setData(bundle);
        msg.what = 1;
        handler.sendMessage(msg);
    }
    StringBuilder stringBuilder = new StringBuilder();
    void setStringShow(int x){
        stringBuilder.append(x);
        if (stringBuilder.length() > 8){
            stringBuilder.deleteCharAt(0);
        }

        setCMD(stringBuilder.toString());
    }
}
