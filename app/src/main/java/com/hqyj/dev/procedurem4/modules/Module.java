package com.hqyj.dev.procedurem4.modules;

import com.hqyj.dev.procedurem4.modules.behaivor.Operate;

/**
 * Created by jiyangkang on 2016/5/6 0006.
 */
public abstract class Module {

    private String name;
    private int switcher;

    public void setSwitcher(int switcher) {
        this.switcher = switcher;
    }

    public int getSwitcher() {
        return switcher;
    }

    public Operate operate;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
