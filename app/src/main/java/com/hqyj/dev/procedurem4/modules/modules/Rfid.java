package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.DCMotorOperate;
import com.hqyj.dev.procedurem4.modules.Operations.RFIDOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Rfid extends Module{

    private volatile static Rfid rfid = null;


    private Rfid(){

        setName(ModulesInfo.application.getResources().getString(R.string.rfid));
        operate = new RFIDOperate();
        int switcher = R.drawable.rfid_bo;
        setSwitcher(switcher);

    }

    public static Rfid getRfid(){
        if (rfid == null){
            synchronized (Rfid.class){
                if (rfid == null)
                    rfid = new Rfid();
            }
        }
        return rfid;
    }



}
