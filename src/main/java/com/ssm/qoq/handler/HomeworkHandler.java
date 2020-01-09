package com.ssm.qoq.handler;

import com.ssm.qoq.entity.Homework;
import com.ssm.qoq.repostity.HomeworkRepostity;
import com.ssm.qoq.response.HomeworkTransaction;
import com.ssm.qoq.response.Result;
import com.ssm.qoq.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/homework")
public class HomeworkHandler {
    @Autowired
    private HomeworkRepostity homeworkRepostity;
    @Autowired
    private HomeworkTransaction homeworkTransaction;
    @PostMapping("/addhomework")
    public Result<Homework> addhomework(@RequestBody  Homework homework){
        if (homework.getList()==null||homework.getList().size()==0||homework.getHomeworkType()==null
        ||homework.getHomeworkTitle()==null||homework.getCourseId()==null){
            return ResultUtil.error("题号为空||类型不能为空||题目不能为空||课程id不能为空");
        }else {
            try {
                homeworkTransaction.addTransaction(homework);
            }catch (RuntimeException r){
                return ResultUtil.error("运行时异常,原因:"+r.getMessage());
            }catch (Exception e){
                return ResultUtil.error("异常,原因:"+e.getMessage());
            }
            return ResultUtil.success("添加成功");
        }
    }
    @PostMapping("/allhomework")
    public Result<List<Homework>> allhomeword(@RequestBody Homework homework){
        List<Homework> list=null;
        if (homework.getHomeworkType()==null||homework.getCourseId()==null){
            return  ResultUtil.error("课程id或作业类型为空");
        }else {
            try {
                list=homeworkRepostity.allhomeword(homework);
            }catch (RuntimeException r){
                return ResultUtil.error("运行时异常,原因:"+r.getMessage());
            }catch (Exception e){
                return ResultUtil.error("异常,原因:"+e.getMessage());
            }
            if (list==null||list.size()==0){
                return ResultUtil.success("数据为空");
            }else {
                for (Homework homework1 : list) {
                    try {
                        homework1.setHomeworkCount(homeworkRepostity.count(homework1.getHomeworkId()));
                    }catch (RuntimeException r){
                        return ResultUtil.error("运行时异常,原因:"+r.getMessage());
                    }catch (Exception e){
                        return ResultUtil.error("异常,原因:"+e.getMessage());
                    }
                }
                return ResultUtil.success(list,list.size());
            }
        }
    }
    @PostMapping("/updatestatus")
    public Result<Homework> updatestatus(@RequestBody  Homework homework){
        if (homework.getHomeworkId()==null||homework.getHomeworkStatus()==null){
            return  ResultUtil.error("作业id和状态不能为空");
        }else {
            try {
                homeworkRepostity.updatestatus(homework);
            }catch (RuntimeException r){
                return ResultUtil.error("运行时异常,原因:"+r.getMessage());
            }catch (Exception e){
                return ResultUtil.error("异常,原因:"+e.getMessage());
            }
            return ResultUtil.success("更改成功");
        }
    }
    @PostMapping("/homeworkdetail")
    public  Result<Homework> homeworkdetail(@RequestBody Homework homework){
        if(homework.getHomeworkId()==null){
            return ResultUtil.error("作业id不能为空");
        }else {
            try{
                homework=homeworkRepostity.homeworkdetail(homework);
            }catch (RuntimeException r){
                return ResultUtil.error("运行时异常,原因:"+r.getMessage());
            }catch (Exception e){
                return ResultUtil.error("异常,原因:"+e.getMessage());
            }
            if(homework==null){
                return  ResultUtil.success("数据为空");
            }
            return ResultUtil.success(homework,1);
        }
    }
}
