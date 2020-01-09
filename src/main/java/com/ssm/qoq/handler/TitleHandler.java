package com.ssm.qoq.handler;

import com.ssm.qoq.entity.Title;
import com.ssm.qoq.repostity.TitleRepostity;
import com.ssm.qoq.response.Result;
import com.ssm.qoq.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/title")
public class TitleHandler {
    @Autowired
    private TitleRepostity titleRepostity;
    @PostMapping("/addtitle")
    public Result<Title> addtitle(@RequestBody ArrayList<Title> list){
        if(list==null||list.size()==0){
            return ResultUtil.error("list为空");
        }else{
            try {
                titleRepostity.addtitle(list);
            }catch (RuntimeException r){
                return ResultUtil.error("运行时异常,原因:"+r.getMessage());
            }catch (Exception e){
                return ResultUtil.error("异常,原因:"+e.getMessage());
            }
            return  ResultUtil.success("添加成功");
        }
    }
    @PostMapping("alltype")
    public Result<List<Title>> alltype(@RequestBody Title title){
        List<Title> list=null;
        if(title.getTitleType()==null){
            return ResultUtil.error("类型不能为空");
        }else {
            try {
                list=titleRepostity.alltype(title);
            }catch (RuntimeException r){
                return ResultUtil.error("运行时异常,原因:"+r.getMessage());
            }catch (Exception e){
                return ResultUtil.error("异常,原因:"+e.getMessage());
            }
            if (list==null||list.size()==0){
                return  ResultUtil.success("数据为空");
            }else {
                return  ResultUtil.success(list,list.size());
            }


        }
    }
}
