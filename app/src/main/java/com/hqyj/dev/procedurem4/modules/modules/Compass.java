package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.BuzzerOperate;
import com.hqyj.dev.procedurem4.modules.Operations.CompassOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Compass extends Module{

    private volatile static Compass compass = null;


    private Compass(){

        setName(ModulesInfo.application.getResources().getString(R.string.compass));
        operate = new CompassOperate();

        int switcher = R.drawable.compass_bo;
        setSwitcher(switcher);
    }

    public static Compass getCompass(){
        if (compass == null){
            synchronized (Compass.class){
                if (compass == null)
                    compass = new Compass();
            }
        }
        return compass;
    }



}
