package com.ssm.qoq.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class InviteCode implements Serializable {
    private  String courseId;
    private  String code;
    private  Long end;
    private  String finish;
}
