package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.DCMotorOperate;
import com.hqyj.dev.procedurem4.modules.Operations.RelayOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Relay extends Module{

    private volatile static Relay relay = null;


    private Relay(){

        setName(ModulesInfo.application.getResources().getString(R.string.relay));
        operate = new RelayOperate();
        int switcher = R.drawable.relay_bo;
        setSwitcher(switcher);

    }

    public static Relay getRelay(){
        if (relay == null){
            synchronized (Relay.class){
                if (relay == null)
                    relay = new Relay();
            }
        }
        return relay;
    }



}
