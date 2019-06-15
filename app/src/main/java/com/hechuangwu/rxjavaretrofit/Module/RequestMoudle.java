package com.hechuangwu.rxjavaretrofit.Module;

/**
 * Created by cwh on 2018/12/15.
 * 功能:
 */
public class RequestMoudle {
    private String skuIds;
    private int type;

    public RequestMoudle(String skuIds, int type) {
        this.skuIds = skuIds;
        this.type = type;
    }

    public String getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(String skuIds) {
        this.skuIds = skuIds;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
