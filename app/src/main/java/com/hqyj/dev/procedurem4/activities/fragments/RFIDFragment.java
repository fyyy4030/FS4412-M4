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
import com.hqyj.dev.procedurem4.modules.modules.Rfid;

/**
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class RFIDFragment extends Fragment {
    private View mView;
    private TextView textView;
    private Rfid rfid;

    private String TAG = "RFID";

    private RfidReadThread rfidReadThread;
    private boolean threadOn = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String datas = changeIntoIntArray(msg.getData().getInt(TAG));
                    textView.setTextSize(30);
                    textView.setText(datas);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rfid = Rfid.getRfid();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.rfid_fragment, container, false);

        initShow();

        threadOn = true;
        rfidReadThread = new RfidReadThread();
        rfidReadThread.start();

        return mView;
    }

    private void initShow() {
        textView = (TextView) mView.findViewById(R.id.txv_rfid);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadOn = false;
        rfidReadThread = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class RfidReadThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (threadOn){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int numbers = rfid.operate.read()[0];
                Bundle bundle = new Bundle();
                bundle.putInt(TAG, numbers);
                Message msg = new Message();
                msg.what = 1;
                msg.setData(bundle);
                handler.sendMessage(msg);


            }
        }
    }

    @SuppressLint("DefaultLocale")
    private String changeIntoIntArray(int number){
        int[] intArray = new int[4];
        String string;
        for (int i = 0; i < 4; i++){
            intArray[i] = (number >> (3-i)*8) & 0x0ff;

        }
        string = String.format("Card ID: %02x %02x %02x %02x",
                intArray[0],intArray[1],intArray[2],intArray[3]);

        return string;
    }


}
