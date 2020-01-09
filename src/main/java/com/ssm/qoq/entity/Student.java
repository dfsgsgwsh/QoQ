package com.ssm.qoq.entity;

import lombok.Data;

@Data
public class Student {
    private Long studentId;
    private String studentName;
    private  String studentStatus;
    private Sign sign;
}
