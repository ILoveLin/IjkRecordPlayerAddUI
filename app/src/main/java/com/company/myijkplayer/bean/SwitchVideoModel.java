package com.company.myijkplayer.bean;

import java.io.Serializable;

/**
 * LoveLin
 * <p>
 * Describe  不同清晰度的数据bean
 */

public class SwitchVideoModel implements Serializable {
    private String url;
    private String name;

    public SwitchVideoModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}