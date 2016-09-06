package com.hqyj.dev.procedurem4.staticBuild;


import android.app.Application;

/**
 *
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class ModulesInfo {

    public static final int ALCOHOL = 1;
    public static final int BRAKE = 5;
    public static final int BUZZER = 6;
    public static final int COMPASS = 7;
    public static final int DCMOTOR = 8;
    public static final int GAS = 4;
    public static final int LIGHT = 2;
    public static final int MATRIX = 9;
    public static final int RELAY = 10;
    public static final int RFID = 11;
    public static final int SERVO = 12;
    public static final int STEEPER = 13;
    public static final int THERMISTOR = 3;
    public static final int TUBE = 14;
    public static final int TEMP = 15;

    public static Application application = null;

    static {
        System.loadLibrary("operate");
    }

}
