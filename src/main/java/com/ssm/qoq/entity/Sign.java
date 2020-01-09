package com.ssm.qoq.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Sign {
    private Integer signId;
    private  Integer courseId;
    private  String signTitle;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private  Date lateTime;
    private  Long truancyTime;
    private  String code;
    private  String createTime;
}
