package com.norm.timemall.app.base.enums;

public enum MsgTypeEnum {
    TEXT("text","文本"),
    IMAGE("image","图片"),
    ATTACHMENT("attachment","附件");
    private String mark;
    private String desc;

    MsgTypeEnum(String mark, String desc) {
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