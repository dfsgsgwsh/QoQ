package com.ssm.qoq.handler;

import com.ssm.qoq.entity.Course;
import com.ssm.qoq.entity.Student;
import com.ssm.qoq.repostity.StudentRepostity;
import com.ssm.qoq.response.Result;
import com.ssm.qoq.response.ResultUtil;
import com.ssm.qoq.response.StudentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentHandler {
    @Autowired
    private StudentRepostity studentRepostity;
    @Autowired
    private StudentTransaction studentTransaction;
    @PostMapping("/addstudent")
    public Result<Student> addstudent(@RequestBody Course course){
        if (course.getCourseId()==null||course.getList()==null||course.getList().size()==0){
            return ResultUtil.error("课程id不能为空||学生列表不能为空");
        }else {
            try {
                studentTransaction.addstudent(course);
            }catch (RuntimeException r){
                return  ResultUtil.error("运行异常,原因:"+r.getMessage());
            }catch (Exception e){
                return  ResultUtil.error("异常,原因:"+e.getMessage());
            }
            return ResultUtil.success("添加成功");
        }
    }
    @PostMapping("/allstudent")
    public Result<List<Student>> allstudent(@RequestBody Course course){
        List<Student> list=null;
        if (course.getCourseId()==null){
            return  ResultUtil.error("课程id不能为空");
        }else {
             try {
                 list=studentRepostity.allstudent(course.getCourseId());
             }catch (RuntimeException r){
                 return  ResultUtil.error("运行异常,原因:"+r.getMessage());
             }catch (Exception e){
                 return  ResultUtil.error("异常,原因:"+e.getMessage());
             }
             if (list==null||list.size()==0){
                 return  ResultUtil.success("数据为空");
             }else {
                 return ResultUtil.success(list,list.size());
             }
        }
    }
}
