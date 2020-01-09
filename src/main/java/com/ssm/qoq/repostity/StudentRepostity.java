package com.ssm.qoq.repostity;

import com.ssm.qoq.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentRepostity {
    public  void  addstudents(@Param("list") List<Student> list);
    public void coursestudent(@Param("list") List<Student> list,@Param("cid") Integer cid);
    public List<Student> allstudent(Integer id);
}
