package com.norm.timemall.app.base.enums;

public enum TransTypeEnum {
    COMMISSION("1","佣金"),
    OTHER("2","其他"),
    TRANSFER("3","交易");
    private String mark;
    private String desc;

    TransTypeEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}