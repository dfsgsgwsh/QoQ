package com.ssm.qoq.entity;

import lombok.Data;

import java.util.List;

@Data
public class Course {
    private  Integer courseId;
    private  Integer termId;
    private  String  courseName;
    private  String  courseDetail;
    private  String  courseIntro;
    private  String courseCode;
    private  Integer courseCount;
    private List<Student> list;
}
