package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.DCMotorOperate;
import com.hqyj.dev.procedurem4.modules.Operations.TubeOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Tube extends Module{

    private volatile static Tube tube = null;


    private Tube(){

        setName(ModulesInfo.application.getResources().getString(R.string.tube));
        operate = new TubeOperate();
        int switcher = R.drawable.tube_bo;
        setSwitcher(switcher);

    }

    public static Tube getTube(){
        if (tube == null){
            synchronized (Tube.class){
                if (tube == null)
                    tube = new Tube();
            }
        }
        return tube;
    }



}
