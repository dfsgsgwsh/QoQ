package com.ssm.qoq.repostity;

import com.ssm.qoq.entity.Homework;
import com.ssm.qoq.entity.Title;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeworkRepostity {
    public void addhomework(Homework homework);
    public void addhomeworktitle(@Param("titlelist") List<Title> list, @Param("homewordId") Integer id);
    public List<Homework> allhomeword(Homework homework);
    public  void updatestatus(Homework homework);
    public Homework homeworkdetail(Homework homework);
    public  Integer count(Integer id);
}
