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

                if (value != null && value[0]!=0 && value[1]!=0) {
                    String valueKey = getKey(value[0]);
                    if (!valueKey.equalsIgnoreCase( "null")) {
                        if (string == null) {
                            string = getKey(value[0]);
                        } else {
                            string = string + getKey(value[0]);
                        }
                        msg.what = 1;
                        b.putString(TAG, string);
                        Log.d(TAG, string);
                        msg.setData(b);
                        handler.sendMessage(msg);
                    }

                    if (string!=null &&string.length() >= 15)
                        string = null;
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private String getKey(int key){
        String value;
        switch (key){
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                value = String.format("%d", key-1);
                break;
            case 11:
                value = String.format("%d", 0);
                break;
            case 30:
                value = "A";
                break;
            case 48:
                value = "B";
                break;
            case 46:
                value = "C";
                break;
            case 32:
                value = "D";
                break;
            case 522:
                value = "*";
                break;
            case 523:
                value = "#";
                break;
            default:
                value = "null";
                break;

        }
        return value;
    }
}
