package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.DCMotorOperate;
import com.hqyj.dev.procedurem4.modules.Operations.SteeperOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Steeper extends Module{

    private volatile static Steeper steeper = null;


    private Steeper(){

        setName(ModulesInfo.application.getResources().getString(R.string.steeper));
        operate = new SteeperOperate();
        int switcher = R.drawable.steeper_bo;
        setSwitcher(switcher);

    }

    public static Steeper getSteeper(){
        if (steeper == null){
            synchronized (Steeper.class){
                if (steeper == null)
                    steeper = new Steeper();
            }
        }
        return steeper;
    }



}
