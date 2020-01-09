package com.ssm.qoq.handler;

import com.ssm.qoq.entity.Sign;
import com.ssm.qoq.entity.Course;
import com.ssm.qoq.entity.InviteCode;
import com.ssm.qoq.entity.Student;
import com.ssm.qoq.repostity.CourseRepostity;
import com.ssm.qoq.response.Result;
import com.ssm.qoq.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/course")
public class CourseHandler {
    @Autowired
    private CourseRepostity courseRepostity;
    @Autowired
    private RedisTemplate<String,InviteCode> numTemplate;
    @Autowired
    private RedisTemplate<String,String> codeTemplate;
    @GetMapping("/allterm/{id}")
    public Result<List<Course>> termall(@PathVariable("id") Integer id) {
            List<Course> list = null;
            try {
                list = courseRepostity.termallcourse(id);
            }catch (RuntimeException r){
                return  ResultUtil.error("运行异常,原因:"+r.getMessage());
            }catch (Exception e){
                return  ResultUtil.error("异常,原因:"+e.getMessage());
            }
            if (list == null||list.size()==0) {
                return ResultUtil.success("数据为空");
            } else {
                for (Course course : list) {
                    try {
                        if (numTemplate.opsForValue().get("InviteCode"+course.getCourseId())!=null){
                            course.setCourseCode(numTemplate.opsForValue().get("InviteCode"+course.getCourseId()).getCode());
                        }
                        course.setCourseCount(courseRepostity.count(course.getCourseId()));
                    }catch (RuntimeException r){
                        return  ResultUtil.error("运行异常,原因:"+r.getMessage());
                    }catch (Exception e){
                        return  ResultUtil.error("异常,原因:"+e.getMessage());
                    }
                }
                return ResultUtil.success(list, list.size());
            }
    }
    @PostMapping("/addcourse")
    public Result<Course> addcourse(@RequestBody Course course){
        if (course.getCourseName()==null||course.getTermId()==null){
            return ResultUtil.error("学期id为空或课程名为空");
        }else {
            try {
                courseRepostity.addcourse(course);
                return ResultUtil.success("请求成功");
            }catch (RuntimeException r){
                return  ResultUtil.error("运行异常,原因:"+r.getMessage());
            }catch (Exception e){
                return  ResultUtil.error("异常,原因:"+e.getMessage());
            }
        }
    }
    @PostMapping("/code")
    public Result<InviteCode> num(@RequestBody InviteCode code){
        String num =null;
        if(code.getCourseId()==null){//判断结束时间是否第一次访问
            return ResultUtil.error("id为空");
        }else {
            if(code.getEnd()==null){
                try {
                    code=numTemplate.opsForValue().get("InviteCode"+code.getCourseId());
                }catch (RuntimeException r){
                    return  ResultUtil.error("运行异常,原因:"+r.getMessage());
                }catch (Exception e){
                    return  ResultUtil.error("异常,原因:"+e.getMessage());
                }
                return ResultUtil.success(code,1);
            }else {
                try {
                    num=UUID.randomUUID().toString().substring(0,7);
                    code.setCode(num);
                    numTemplate.opsForValue().set("InviteCode"+code.getCourseId(),code,code.getEnd(),TimeUnit.SECONDS);
                }catch (RuntimeException r){
                    return  ResultUtil.error("运行异常,原因:"+r.getMessage());
                }catch (Exception e){
                    return  ResultUtil.error("异常,原因:"+e.getMessage());
                }
                return ResultUtil.success(code,1);
            }
        }

    }
    @GetMapping("/allstudent/{id}")
    public Result<Course> courseallstudent(@PathVariable("id") Integer id){
        Course course=null;
        try {
            course=courseRepostity.courseallstudent(id);
        }catch (RuntimeException r){
            return  ResultUtil.error("运行异常,原因:"+r.getMessage());
        }catch (Exception e){
            return  ResultUtil.error("异常,原因:"+e.getMessage());
        }
        if (course==null){
            return ResultUtil.success("数据为空");
        }else {
            return ResultUtil.success(course,1);
        }
    }
    @PostMapping("/checkcode")
    public Result<Sign> checkCodeResult(@RequestBody Sign sign){
        if (sign.getCourseId()==null|| sign.getLateTime()==null|| sign.getTruancyTime()==null){
            return ResultUtil.error("课程id,迟到时间,旷课时间不能为空");
        }else {
            try {
                String num=UUID.randomUUID().toString().substring(0,7);
                codeTemplate.opsForValue().set("code"+ sign.getCourseId(),num, sign.getTruancyTime(),TimeUnit.SECONDS);
                sign.setCode(num);
                courseRepostity.addsign(sign);
            }catch (RuntimeException r){
                return  ResultUtil.error("运行异常,原因:"+r.getMessage());
            }catch (Exception e){
                return  ResultUtil.error("异常,原因:"+e.getMessage());
            }
            return ResultUtil.success(sign,1);
        }
    }
    @GetMapping("/getcheckcode/{id}")
    public  Result<Sign> checkCodeResult(@PathVariable("id") Integer id){
        Sign sign=null;
        try {
            sign =courseRepostity.selectsign(id);
        }catch (RuntimeException r){
            return  ResultUtil.error("运行异常,原因:"+r.getMessage());
        }catch (Exception e){
            return  ResultUtil.error("异常,原因:"+e.getMessage());
        }
        if(codeTemplate.opsForValue().get("code"+id)==null){
            return ResultUtil.success(sign,1);
        }else {
            String num=codeTemplate.opsForValue().get("code"+id);
            sign.setCode(num);
            return ResultUtil.success(sign,1);
        }

    }
    @GetMapping("/allsign/{id}")
    public Result<List<Sign>> allsign(@PathVariable("id") Integer id){
        List<Sign> list=null;
        try {
            list=courseRepostity.allsign(id);
        }catch (RuntimeException r){
            return  ResultUtil.error("运行异常,原因:"+r.getMessage());
        }catch (Exception e){
            return  ResultUtil.error("异常,原因:"+e.getMessage());
        }
        if (list==null ||list.size()==0){
            return ResultUtil.success("数据为空");
        }else {
            Sign sign1=list.get(0);
            try {
                sign1.setCode(codeTemplate.opsForValue().get("code"+ sign1.getCourseId()));
            }catch (RuntimeException r){
                return  ResultUtil.error("运行异常,原因:"+r.getMessage());
            }catch (Exception e){
                return  ResultUtil.error("异常,原因:"+e.getMessage());
            }
            return  ResultUtil.success(list,list.size());
        }
    }
    @PostMapping("/signdetail")
    public Result<List<Student>> signdetail(@RequestBody Sign sign){
        if (sign.getCourseId()==null||sign.getSignId()==null){
            return  ResultUtil.error("签到id和课程不能为空");
        }else {
            ArrayList<Student> list=null;
            try {
                list=courseRepostity.signdetail(sign.getCourseId(),sign.getSignId());
            }catch (RuntimeException r){
                return  ResultUtil.error("运行异常,原因:"+r.getMessage());
            }catch (Exception e){
                return  ResultUtil.error("异常,原因:"+e.getMessage());
            }
            if (list==null||list.size()==0){
                return ResultUtil.success("数据为空");
            }else {
                if (list.get(0).getSign()==null){
                    try {
                        Sign sign1=courseRepostity.onesign(sign.getSignId());
                        sign1.setCode(codeTemplate.opsForValue().get("code"+ sign1.getCourseId()));
                        list.get(0).setSign(sign1);
                    }catch (RuntimeException r){
                        return  ResultUtil.error("运行异常,原因:"+r.getMessage());
                    }catch (Exception e){
                        return  ResultUtil.error("异常,原因:"+e.getMessage());
                    }
                }
                return ResultUtil.success(list,list.size());
            }
        }
    }
}
