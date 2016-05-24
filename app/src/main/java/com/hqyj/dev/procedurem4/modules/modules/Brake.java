package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.BrakeOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Brake extends Module{

    private volatile static Brake brake = null;


    private Brake(){

        setName(ModulesInfo.application.getResources().getString(R.string.brake));
        operate = new BrakeOperate();
        int switcher = R.drawable.brake_bo;
        setSwitcher(switcher);
    }

    public static Brake getBrake(){
        if (brake == null){
            synchronized (Brake.class){
                if (brake == null)
                    brake = new Brake();
            }
        }
        return brake;
    }



}
