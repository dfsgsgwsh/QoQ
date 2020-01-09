package com.ssm.qoq.repostity;

import com.ssm.qoq.entity.Course;
import com.ssm.qoq.entity.Sign;
import com.ssm.qoq.entity.Student;

import java.util.ArrayList;
import java.util.List;

public interface CourseRepostity {
    public List<Course> termallcourse(Integer id);
    public void addcourse(Course course);
    public  Course courseallstudent(Integer id);
    public  Integer count(Integer id);
    public  void addsign(Sign sign);
    public  Sign selectsign(Integer id);
    public  List<Sign> allsign(Integer id);
    public ArrayList<Student> signdetail(Integer cid, Integer sid);
    public  Sign onesign(Integer id);
}
