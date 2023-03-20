package com.cyn.service;

import com.cyn.pojo.Disease;
import com.cyn.pojo.PageBean;

import java.util.List;

public interface DiseaseService {

    List<Disease> selectAll();//不用

    void add(Disease disease);

    List<Disease> selectByCropDiseaseName(Disease disease);//不用

    void deleteByids(int[] ids);

    PageBean<Disease> selectByPage(int currentPage,int pageSize);//不用

    PageBean<Disease> selectByPageAndCondition(int currentPage,int pageSize,Disease disease);
}
