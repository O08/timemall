package com.norm.timemall.app.ms.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MsMpsEvent {
    private String room;
    private ArrayList<MsMpsEventRecord> records;
}