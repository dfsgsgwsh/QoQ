package com.ssm.qoq.handler;

import com.ssm.qoq.entity.Term;
import com.ssm.qoq.repostity.TermRepostity;
import com.ssm.qoq.response.Result;
import com.ssm.qoq.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/term")
public class TermHandler {
    @Autowired
    private TermRepostity termReposity;
    @GetMapping("/all")
    public Result<List<Term>> list(){
        List<Term> list=null;
        try {
            list=termReposity.allterm();
        }catch (RuntimeException r){
            return ResultUtil.error("运行时异常,原因:"+r.getMessage());
        }catch (Exception e){
            return ResultUtil.error("异常,原因:"+e.getMessage());
        }
        if (list==null||list.size()==0){
            return ResultUtil.success("数据为空");
        }else {
            return ResultUtil.success(termReposity.allterm(),list.size());
        }

    }
    @PostMapping("/add")
    public  Result<Term> add(@RequestBody Term term){
        if (term.getTermNo()==null||term.getTermYear()==null){
            return ResultUtil.error("学期年份为空或者学期号为空");
        }else {
            try {
                termReposity.addterm(term);
            }catch (RuntimeException r){
                return ResultUtil.error("运行时异常,原因:"+r.getMessage());
            }catch (Exception e){
                return ResultUtil.error("异常,原因:"+e.getMessage());
            }
            return ResultUtil.success("请求成功");
        }

    }
}
