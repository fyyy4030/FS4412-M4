package com.hqyj.dev.procedurem4.modules.Operations;

import com.hqyj.dev.procedurem4.modules.behaivor.Operate;
import com.hqyj.dev.procedurem4.staticBuild.ModulesInfo;

/**
 *
 * Created by jiyangkang on 2016/5/9 0009.
 */
public class GasOperate implements Operate {


    @Override
    public boolean init() {
        return false;
    }

    @Override
    public int[] read() {

        int[] value = NativieOperate.readModule(ModulesInfo.GAS);

        return value;
    }

    @Override
    public boolean write(int operate) {
        return false;
    }


}
