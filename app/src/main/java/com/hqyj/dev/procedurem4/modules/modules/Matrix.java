package com.hqyj.dev.procedurem4.modules.modules;

import com.hqyj.dev.procedurem4.R;
import com.hqyj.dev.procedurem4.modules.Module;
import com.hqyj.dev.procedurem4.modules.Operations.DCMotorOperate;
import com.hqyj.dev.procedurem4.modules.Operations.MatrixOperate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public class Matrix extends Module{

    private volatile static Matrix matrix = null;


    private Matrix(){

        setName(ModulesInfo.application.getResources().getString(R.string.marix));
        operate = new MatrixOperate();
        int switcher = R.drawable.matrix_bo;
        setSwitcher(switcher);

    }

    public static Matrix getMatrix(){
        if (matrix == null){
            synchronized (Matrix.class){
                if (matrix == null)
                    matrix = new Matrix();
            }
        }
        return matrix;
    }



}
