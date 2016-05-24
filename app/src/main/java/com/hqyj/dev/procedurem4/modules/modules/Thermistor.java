package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.DCMotorOperate;
import com.hqyj.dev.procedurem4.modules.Operations.ThermistorOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Thermistor extends Module{

    private volatile static Thermistor thermistor = null;


    private Thermistor(){

        setName(ModulesInfo.application.getResources().getString(R.string.thermistor));
        operate = new ThermistorOperate();
        int switcher = R.drawable.thermistor_bo;
        setSwitcher(switcher);

    }

    public static Thermistor getThermistor(){
        if (thermistor == null){
            synchronized (Thermistor.class){
                if (thermistor == null)
                    thermistor = new Thermistor();
            }
        }
        return thermistor;
    }



}
