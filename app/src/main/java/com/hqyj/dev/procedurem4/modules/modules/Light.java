package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.DCMotorOperate;
import com.hqyj.dev.procedurem4.modules.Operations.LightOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Light extends Module{

    private volatile static Light light = null;


    private Light(){

        setName(ModulesInfo.application.getResources().getString(R.string.light));
        operate = new LightOperate();
        int switcher = R.drawable.light_bo;
        setSwitcher(switcher);

    }

    public static Light getLight(){
        if (light == null){
            synchronized (Light.class){
                if (light == null)
                    light = new Light();
            }
        }
        return light;
    }



}
