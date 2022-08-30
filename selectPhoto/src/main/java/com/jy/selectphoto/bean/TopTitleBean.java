package com.jy.selectphoto.bean;

import java.io.Serializable;

/**
 */

public class TopTitleBean implements Serializable {
    public String firstPath;
    public String name;
    public int num;

    public TopTitleBean(int num, String firstPath, String name) {
        this.num = num;
        this.firstPath = firstPath;
        this.name = name;
    }
}
