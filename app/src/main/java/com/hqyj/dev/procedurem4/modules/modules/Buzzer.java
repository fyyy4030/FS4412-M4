package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.BrakeOperate;
import com.hqyj.dev.procedurem4.modules.Operations.BuzzerOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Buzzer extends Module{

    private volatile static Buzzer buzzer = null;


    private Buzzer(){

        setName(ModulesInfo.application.getResources().getString(R.string.buzzer));
        operate = new BuzzerOperate();
        int switcher = R.drawable.buzzer_bo;
        setSwitcher(switcher);

    }

    public static Buzzer getBuzzer(){
        if (buzzer == null){
            synchronized (Buzzer.class){
                if (buzzer == null)
                    buzzer = new Buzzer();
            }
        }
        return buzzer;
    }



}
