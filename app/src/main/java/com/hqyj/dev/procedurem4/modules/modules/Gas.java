package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.CompassOperate;
import com.hqyj.dev.procedurem4.modules.Operations.GasOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Gas extends Module{

    private volatile static Gas gas = null;


    private Gas(){

        setName(ModulesInfo.application.getResources().getString(R.string.gas));
        operate = new GasOperate();
        int switcher = R.drawable.gas_bo;
        setSwitcher(switcher);

    }

    public static Gas getGas(){
        if (gas == null){
            synchronized (Gas.class){
                if (gas == null)
                    gas = new Gas();
            }
        }
        return gas;
    }



}
