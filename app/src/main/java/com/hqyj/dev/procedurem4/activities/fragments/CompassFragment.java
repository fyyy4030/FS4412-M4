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
import com.hqyj.dev.procedurem4.modules.modules.Compass;

import view.DrawCompass;

/**
 *
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class CompassFragment extends Fragment {

    private View mView;
    private Compass compass;
    private boolean threadOn;
    private CompassReadThread compassReadThread;

    private TextView textView;
    private DrawCompass drawCompass;
    private String TAG = "Compass";
    private String TAG_1 = "Compass_1";

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
                    int valueI = msg.getData().getInt(TAG_1);
                    textView.setTextSize(15);
//                    Log.d(TAG, ""+valueI);
                    textView.setText(String.format("%s", value));
                    drawCompass.setDegree(valueI);
                    drawCompass.invalidate();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compass = Compass.getCompass();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.compass_fragment, container, false);

        initShow();
        threadOn = true;
        compassReadThread = new CompassReadThread();
        compassReadThread.start();
        return mView;
    }

    private void initShow() {
        textView = (TextView) mView.findViewById(R.id.txv_compass);
        drawCompass = (DrawCompass) mView.findViewById(R.id.draw_compass);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadOn = false;
        compassReadThread.interrupt();
    }

    private class CompassReadThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(threadOn){
                Bundle b = new Bundle();
                Message msg = new Message();

                int value = compass.operate.read()[0];

                String result = doAngle(value);
                b.putInt(TAG_1, value);
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

    @SuppressLint("DefaultLocale")
    private String doAngle(int angle){

        if (angle > 270){
            angle = angle - 270;
        } else {
            angle = angle + 90;
        }

        String string;

        if (angle < 22.5){
            string = String.format("当前方向：%s,南偏西%d", "正南", angle);
        } else if (angle > 337.5 && angle <= 360){
            string = String.format("当前方向：%s,南偏东%d", "正南", 360 - angle);
        }else if (angle > 22.5 && angle <= 45){
            string = String.format("当前方向：%s,西南偏南%d", "西南", 45 - angle);
        } else if (angle > 45 && angle < 65.5){
            string = String.format("当前方向：%s,西南偏西%d", "西南", angle - 45);
        } else if (angle > 65.5 && angle <= 90){
            string = String.format("当前方向：%s,西偏南%d", "正西", 90 - angle);
        } else if (angle > 90 && angle < 112.5){
            string = String.format("当前方向：%s,西偏北%d", "正西", angle - 90);
        }else if (angle > 112.5 && angle <= 135){
            string = String.format("当前方向：%s,西北偏西%d", "西北", 135 - angle);
        } else if (angle > 135 && angle < 157.5){
            string = String.format("当前方向：%s,西北偏北%d", "西北", angle - 135);
        }else if (angle > 157.5 && angle <= 180){
            string = String.format("当前方向：%s,北偏西%d", "正北", 180 - angle);
        } else if (angle > 180 && angle < 202.5){
            string = String.format("当前方向：%s,北偏东%d", "正北", angle - 180);
        }else if (angle > 202.5 && angle <= 225){
            string = String.format("当前方向：%s,东北偏北%d", "东北", 225 - angle);
        } else if (angle > 225 && angle < 247.5){
            string = String.format("当前方向：%s,东北偏东%d", "东北", angle - 225);
        }else if (angle > 247.5 && angle <= 270){
            string = String.format("当前方向：%s,东偏北%d", "正东", 270 - angle);
        } else if (angle > 270 && angle < 292.5){
            string = String.format("当前方向：%s,东偏南%d", "正东", angle - 270);
        }else if (angle > 292.5 && angle <= 315){
            string = String.format("当前方向：%s,东南偏东%d", "东南", 315 - angle);
        } else if (angle > 315 && angle < 337.5){
            string = String.format("当前方向：%s,东南偏南%d", "东南", angle - 315);
        }else {
            string = "degree error";
        }

        return string;
    }
}
