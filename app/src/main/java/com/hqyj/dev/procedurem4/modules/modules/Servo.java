package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.DCMotorOperate;
import com.hqyj.dev.procedurem4.modules.Operations.ServoOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Servo extends Module{

    private volatile static Servo servo = null;


    private Servo(){

        setName(ModulesInfo.application.getResources().getString(R.string.servo));
        operate = new ServoOperate();
        int switcher = R.drawable.servo_bo;
        setSwitcher(switcher);

    }

    public static Servo getServo(){
        if (servo == null){
            synchronized (Servo.class){
                if (servo == null)
                    servo = new Servo();
            }
        }
        return servo;
    }



}
