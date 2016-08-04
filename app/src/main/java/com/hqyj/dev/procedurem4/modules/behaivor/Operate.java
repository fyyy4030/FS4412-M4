package com.hqyj.dev.procedurem4.modules.behaivor;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public interface Operate {
    boolean init();
    int[] read();
    boolean write(int operate);
}
