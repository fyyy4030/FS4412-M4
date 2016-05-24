package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.CompassOperate;
import com.hqyj.dev.procedurem4.modules.Operations.DCMotorOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class DCMotor extends Module{

    private volatile static DCMotor dcMotor = null;


    private DCMotor(){

        setName(ModulesInfo.application.getResources().getString(R.string.dc_motor));
        operate = new DCMotorOperate();
        int switcher = R.drawable.dc_motor_bo;
        setSwitcher(switcher);

    }

    public static DCMotor getDcMotor(){
        if (dcMotor == null){
            synchronized (DCMotor.class){
                if (dcMotor == null)
                    dcMotor = new DCMotor();
            }
        }
        return dcMotor;
    }



}
