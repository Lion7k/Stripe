package com.liuzq.stripe.bean;

public enum DefaultCurType implements CurType {
    CNY("人民币"),
    USD("美元"),
    HKD("港币"),
    MOP("澳门元"),
    EUR("欧元"),
    TWD("新台币"),
    KRW("韩元"),
    JPY("日元"),
    SGD("新加坡元"),
    AUD("澳大利亚元");

    private String name;

    private DefaultCurType(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return this.name();
    }

    @Override
    public String getName() {
        return this.name;
    }
}


