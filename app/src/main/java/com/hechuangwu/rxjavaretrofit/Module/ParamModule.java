package com.hechuangwu.rxjavaretrofit.Module;

/**
 * Created by cwh on 2018/12/15.
 * 功能:
 */
public class ParamModule {
//    [{"op":"459.00","m":"859.00","id":"J_954086","p":"-1.00"}]
    private String op;
    private String m;
    private String id;
    private String p;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "ParamModule{" +
                "op='" + op + '\'' +
                ", m='" + m + '\'' +
                ", id='" + id + '\'' +
                ", p='" + p + '\'' +
                '}';
    }
}
