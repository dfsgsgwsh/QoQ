package com.ssm.qoq.repostity;

import com.ssm.qoq.entity.Title;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TitleRepostity {
    public  void addtitle(@Param("titlelist") List<Title> list);
    public  List<Title> alltype(Title title);
}
