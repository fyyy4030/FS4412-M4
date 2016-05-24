package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.AlcoholOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Alcohol extends Module {

    private volatile static Alcohol alcohol = null;


    private Alcohol() {

        setName(ModulesInfo.application.getResources().getString(R.string.alcohol));
        operate = new AlcoholOperate();
        int switcher = R.drawable.alcohol_bo;
        setSwitcher(switcher);
    }

    public static Alcohol getAlcohol() {
        if (alcohol == null) {
            synchronized (Alcohol.class) {
                if (alcohol == null)
                    alcohol = new Alcohol();
            }
        }
        return alcohol;
    }



}
