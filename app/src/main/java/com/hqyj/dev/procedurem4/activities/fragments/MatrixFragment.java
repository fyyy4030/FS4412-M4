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
import com.hqyj.dev.procedurem4.modules.modules.Matrix;

/**
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class MatrixFragment extends Fragment {

    private View mView;

    private boolean threadOn = false;
    private MatrixReadThread matrixReadThread;
    private TextView textView;

    private String string;

    private String TAG = "Matrix";

    private Matrix matrix;

    static {
        System.loadLibrary("operate");
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String value = msg.getData().getString(TAG);
                    textView.setTextSize(50);
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
        matrix = Matrix.getMatrix();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.matrix_fragment, container, false);

        initShow();
        threadOn = true;
        matrixReadThread = new MatrixReadThread();
        matrixReadThread.start();
        return mView;
    }

    private void initShow() {
        textView = (TextView) mView.findViewById(R.id.txv_matrix);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadOn = false;
        matrixReadThread.interrupt();
        matrixReadThread = null;
        string = null;
    }

    private class MatrixReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn) {
                Bundle b = new Bundle();
                Message msg = new Message();
                int[] value = matrix.operate.read();

//                if (value != null && value[0]!=0 && value[1]!=0) {
//                    String valueKey = getKey(value[0]);
//                    if (!valueKey.equalsIgnoreCase( "null")) {
//                        if (string == null) {
//                            string = getKey(value[0]);
//                        } else {
//                            string = string + getKey(value[0]);
//                        }
//                        msg.what = 1;
//                        b.putString(TAG, string);
//                        Log.d(TAG, string);
//                        msg.setData(b);
//                        handler.sendMessage(msg);
//                    }
//
//                    if (string!=null &&string.length() >= 15)
//                        string = null;
//                }

                if (value != null && value[0] != 0 && value[1] != 0){
                    Log.d(TAG, "run: "+"read matrix" + value[0]);
                    String valueKey = getKey(value[0]);
                    if (!valueKey.equalsIgnoreCase("null")){
                        if (string == null){
                            string = valueKey;
                        }else {
                            string = string + valueKey;
                        }
                        msg.what = 1;
                        b.putString(TAG, string);
                        Log.d(TAG, "matrix == === " + string);
                        msg.setData(b);
                        handler.sendMessage(msg);
                    }
                }

                if (string!=null && string.length() >= 15)
                    string = null;
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private String getKey(int key){
        String value;
        switch (key){
            case 28:
                value = String.format("%d", 1);
                break;
            case 27:
                value = String.format("%d", 2);
                break;
            case 26:
                value = String.format("%d", 3);
                break;
            case 20:
                value = String.format("%d", 4);
                break;
            case 19:
                value = String.format("%d", 5);
                break;
            case 18:
                value = String.format("%d", 6);
                break;
            case 12:
                value = String.format("%d", 7);
                break;
            case 11:
                value = String.format("%d", 8);
                break;
            case 10:
                value = String.format("%d", 9);
                break;
            case 3:
                value = String.format("%d", 0);
                break;
            case 25:
                value = "A";
                break;
            case 17:
                value = "B";
                break;
            case 9:
                value = "C";
                break;
            case 1:
                value = "D";
                break;
            case 4:
                value = "*";
                break;
            case 2:
                value = "#";
                break;
            default:
                value = "null";
                break;

        }
        return value;
    }
}
