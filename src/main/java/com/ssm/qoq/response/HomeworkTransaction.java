package com.ssm.qoq.response;

import com.ssm.qoq.entity.Homework;
import com.ssm.qoq.entity.Title;
import com.ssm.qoq.exception.MyException;
import com.ssm.qoq.repostity.HomeworkRepostity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class HomeworkTransaction {
    @Autowired
    private HomeworkRepostity homeworkRepostity;
    @Transactional(rollbackFor = Exception.class)
    public void addTransaction(Homework homework){
        List<Title> list=homework.getList();
        homeworkRepostity.addhomework(homework);
        for (Title title : list) {
            if (title.getTitleId()==null){
                throw new MyException("题目id为空");
            }
        }
        homeworkRepostity.addhomeworktitle(homework.getList(),homework.getHomeworkId());
    }
}
