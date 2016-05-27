package com.hqyj.dev.procedurem4.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.modules.Relay;

/**
 *
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class RelayFragment extends Fragment implements View.OnClickListener{

    private View mView;
    private Relay relay;

    static {
        System.loadLibrary("operate");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relay = Relay.getRelay();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.relay_fragment, container, false);

        initShow();
        mView.findViewById(R.id.btn_relay_open).setOnClickListener(this);
        mView.findViewById(R.id.btn_relay_close).setOnClickListener(this);

        return mView;
    }

    private void initShow() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        relay.operate.write(3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_relay_open:
                relay.operate.write(1);
                break;
            case R.id.btn_relay_close:
                relay.operate.write(0);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        relay.operate.write(3);
    }
}
