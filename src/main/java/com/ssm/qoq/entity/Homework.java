package com.ssm.qoq.entity;

import lombok.Data;

import java.util.List;

@Data
public class Homework {
    private  Integer homeworkId;
    private  Integer courseId;
    private  Integer homeworkCount;
    private  String  homeworkTitle;
    private  String  homeworkType;
    private  String  homeworkTime;
    private  String  homeworkStatus;
    private List<Title> list;
}
