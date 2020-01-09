package com.ssm.qoq.response;

import com.ssm.qoq.entity.Course;
import com.ssm.qoq.entity.Student;
import com.ssm.qoq.exception.MyException;
import com.ssm.qoq.repostity.StudentRepostity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class StudentTransaction {
    @Autowired
    private StudentRepostity studentRepostity;
    @Transactional(rollbackFor = Exception.class)
    public void  addstudent(Course course){
        List<Student> list=course.getList();
        for (Student student : list) {
            if (student.getStudentId()==null||student.getStudentName()==null){
                throw  new MyException("学生id||学生名称为空");
            }
        }
        studentRepostity.addstudents(course.getList());
        studentRepostity.coursestudent(course.getList(),course.getCourseId());
    }
}
