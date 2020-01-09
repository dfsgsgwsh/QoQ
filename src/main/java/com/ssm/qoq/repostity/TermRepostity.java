package com.ssm.qoq.repostity;

import com.ssm.qoq.entity.Term;

import java.util.List;

public interface TermRepostity {
    public List<Term> allterm();
    public  void addterm(Term term);
}
