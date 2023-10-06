package com.qupris;

public class MethodModel {

    String method;
    String pii;

    public MethodModel(String method, String pii) {
        this.method = method;
        this.pii = pii;
    }

    public String getMethod() {
        return method;
    }

    public String getPii() {
        return pii;
    }
}
