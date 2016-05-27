package com.hqyj.dev.procedurem4.activities;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.activities.adapter.ListViewAdapter;
import com.hqyj.dev.procedurem4.activities.adapter.SwitchAdapter;
import com.hqyj.dev.procedurem4.activities.fragments.AlcoholFragment;
import com.hqyj.dev.procedurem4.activities.fragments.BrakeFragment;
import com.hqyj.dev.procedurem4.activities.fragments.BuzzerFragment;
import com.hqyj.dev.procedurem4.activities.fragments.CompassFragment;
import com.hqyj.dev.procedurem4.activities.fragments.DCMotorFragment;
import com.hqyj.dev.procedurem4.activities.fragments.GasFragment;
import com.hqyj.dev.procedurem4.activities.fragments.LightFragment;
import com.hqyj.dev.procedurem4.activities.fragments.MatrixFragment;
import com.hqyj.dev.procedurem4.activities.fragments.RFIDFragment;
import com.hqyj.dev.procedurem4.activities.fragments.RelayFragment;
import com.hqyj.dev.procedurem4.activities.fragments.ServoFragment;
import com.hqyj.dev.procedurem4.activities.fragments.SteeperFragment;
import com.hqyj.dev.procedurem4.activities.fragments.ThermistorFragment;
import com.hqyj.dev.procedurem4.activities.fragments.TubeFragment;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.modules.Alcohol;
import com.hqyj.dev.procedurem4.modules.modules.Brake;
import com.hqyj.dev.procedurem4.modules.modules.Buzzer;
import com.hqyj.dev.procedurem4.modules.modules.Compass;
import com.hqyj.dev.procedurem4.modules.modules.DCMotor;
import com.hqyj.dev.procedurem4.modules.modules.Gas;
import com.hqyj.dev.procedurem4.modules.modules.Light;
import com.hqyj.dev.procedurem4.modules.modules.Matrix;
import com.hqyj.dev.procedurem4.modules.modules.Relay;
import com.hqyj.dev.procedurem4.modules.modules.Rfid;
import com.hqyj.dev.procedurem4.modules.modules.Servo;
import com.hqyj.dev.procedurem4.modules.modules.Steeper;
import com.hqyj.dev.procedurem4.modules.modules.Thermistor;
import com.hqyj.dev.procedurem4.modules.modules.Tube;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import view.DrawListViewItem;
import view.DrawSwitcher;

/**
 * Created by jiyangkang on 2016/5/5 0005.
 */
public class MainActivity extends FragmentActivity implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener {


    private ViewPager viewPager;

    private List<Fragment> fragmentList;


    private ListView listView;
    private List<Module> modulesNameList;
    private View oldView = null;
    int oldPositon = -1;

    private DrawListViewItem drawListViewItem;
    private View view;
    private int position;

    private final String TAG = "Main";

    private TextView txvShowModuleName;
    private DrawSwitcher drawSwitcher;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    if (position != oldPositon) {
                        if (oldView != null) {
                            drawListViewItem = (DrawListViewItem) oldView.findViewById(R.id.item_list);
                            drawListViewItem.setIsChoose(false);
                            drawListViewItem.invalidate();
                        }
                        drawListViewItem = (DrawListViewItem) view.findViewById(R.id.item_list);
                        drawListViewItem.setIsChoose(true);
                        drawListViewItem.invalidate();

                        oldPositon = position;
                        oldView = view;
                    }
                    break;
                case 2:
                    if (view != oldView) {
                        drawListViewItem = (DrawListViewItem) view.findViewById(R.id.item_list);
                        drawListViewItem.setIsChoose(true);
                        drawListViewItem.invalidate();

                        if (oldView != null) {
                            drawListViewItem = (DrawListViewItem) oldView.findViewById(R.id.item_list);
                            drawListViewItem.setIsChoose(false);
                            drawListViewItem.invalidate();
                        }
                    }

                    oldView = view;
                    oldPositon = position;
                    viewPager.setCurrentItem(position);
                    break;

                case 3:
                    int get = msg.getData().getInt(TAG);

                    Module module = modulesNameList.get(get);

                    txvShowModuleName.setText(module.getName());
                    Log.d(TAG, module.getName());
                    drawSwitcher.setBitmap(module.getSwitcher());
                    drawSwitcher.invalidate();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置全屏和无标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_activity);

        ModulesInfo.application = getApplication();

        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initView() {

        txvShowModuleName = (TextView) findViewById(R.id.txv_show_module_name);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        listView = (ListView) findViewById(R.id.list_view);
        drawSwitcher = (DrawSwitcher) findViewById(R.id.draw_switcher);

        fragmentList = new ArrayList<>();
        modulesNameList = new ArrayList<>();


        setFragmentList();


        SwitchAdapter switchAdapter = new SwitchAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(switchAdapter);

        ListViewAdapter listViewAdapter = new ListViewAdapter(this, modulesNameList);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(this);
        viewPager.addOnPageChangeListener(this);
    }


    private void setFragmentList() {


        AlcoholFragment alcoholFragment = new AlcoholFragment();
        BrakeFragment brakeFragment = new BrakeFragment();
        BuzzerFragment buzzerFragment = new BuzzerFragment();
        CompassFragment compassFragment = new CompassFragment();
        DCMotorFragment dcMotorFragment = new DCMotorFragment();
        GasFragment gasFragment = new GasFragment();
        LightFragment lightFragment = new LightFragment();
        MatrixFragment matrixFragment = new MatrixFragment();
        RelayFragment relayFragment = new RelayFragment();
        RFIDFragment rfidFragment = new RFIDFragment();
        ServoFragment servoFragment = new ServoFragment();
        SteeperFragment steeperFragment = new SteeperFragment();
        ThermistorFragment thermistorFragment = new ThermistorFragment();
        TubeFragment tubeFragment = new TubeFragment();

        fragmentList.add(alcoholFragment);
        fragmentList.add(brakeFragment);
        fragmentList.add(buzzerFragment);
        fragmentList.add(compassFragment);
        fragmentList.add(dcMotorFragment);
        fragmentList.add(gasFragment);
        fragmentList.add(lightFragment);
        fragmentList.add(matrixFragment);
        fragmentList.add(relayFragment);
        fragmentList.add(rfidFragment);
        fragmentList.add(servoFragment);
        fragmentList.add(steeperFragment);
        fragmentList.add(thermistorFragment);
        fragmentList.add(tubeFragment);

        modulesNameList.add(fragmentList.indexOf(alcoholFragment),
                Alcohol.getAlcohol());
        modulesNameList.add(fragmentList.indexOf(brakeFragment),
                Brake.getBrake());
        modulesNameList.add(fragmentList.indexOf(buzzerFragment),
                Buzzer.getBuzzer());
        modulesNameList.add(fragmentList.indexOf(compassFragment),
                Compass.getCompass());
        modulesNameList.add(fragmentList.indexOf(dcMotorFragment),
                DCMotor.getDcMotor());
        modulesNameList.add(fragmentList.indexOf(gasFragment),
                Gas.getGas());
        modulesNameList.add(fragmentList.indexOf(lightFragment),
                Light.getLight());
        modulesNameList.add(fragmentList.indexOf(matrixFragment),
                Matrix.getMatrix());
        modulesNameList.add(fragmentList.indexOf(relayFragment),
                Relay.getRelay());
        modulesNameList.add(fragmentList.indexOf(rfidFragment),
                Rfid.getRfid());
        modulesNameList.add(fragmentList.indexOf(servoFragment),
                Servo.getServo());
        modulesNameList.add(fragmentList.indexOf(steeperFragment),
                Steeper.getSteeper());
        modulesNameList.add(fragmentList.indexOf(thermistorFragment),
                Thermistor.getThermistor());
        modulesNameList.add(fragmentList.indexOf(tubeFragment),
                Tube.getTube());
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("TEST", position + ":" + id + ":");
        this.position = position;
        this.view = view;
        handler.sendEmptyMessage(2);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {

        this.position = position;
        int child = listView.getChildCount();

        int hChild = child - child / 2;

        Message msgPosition = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, position);
        msgPosition.setData(bundle);
        msgPosition.what = 3;
        handler.sendMessage(msgPosition);


        if (position < hChild - 1) {
            listView.setSelection(0);
            view = listView.getChildAt(position);
        } else if (position > modulesNameList.size() - hChild) {
            listView.setSelection(modulesNameList.size() - child);

            view = listView.getChildAt(position - child);
        } else {
            listView.setSelection(position - hChild + 1);
            //从前往后，向上
            if (oldPositon < position) {
                if (position == hChild - 1) {
                    view = listView.getChildAt(hChild - 1);
                } else {
                    view = listView.getChildAt(hChild);
                }
            } else {//从后往前，向下
                if (position == modulesNameList.size() - hChild) {
                    view = listView.getChildAt(hChild -1);
                } else {
                    view = listView.getChildAt(hChild - 2);
                }
            }
        }

        handler.sendEmptyMessage(1);

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
