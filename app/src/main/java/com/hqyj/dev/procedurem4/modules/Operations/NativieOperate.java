package com.hqyj.dev.procedurem4.modules.Operations;

/**
 * Created by jiyangkang on 2016/5/9 0009.
 */
public class NativieOperate {


    public static native boolean writeModule(int which, int operate);
    public static native int[] readModule(int which);

}
