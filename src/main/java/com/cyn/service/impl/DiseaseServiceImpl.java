package com.cyn.service.impl;

import com.cyn.mapper.DiseaseMapper;
import com.cyn.pojo.Disease;
import com.cyn.pojo.PageBean;
import com.cyn.service.DiseaseService;
import com.cyn.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class DiseaseServiceImpl implements DiseaseService {

    //创建sqlSessionFactory工厂对象
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    public List<Disease> selectAll() {

        SqlSession sqlSession = factory.openSession();

        DiseaseMapper diseaseMapper = sqlSession.getMapper(DiseaseMapper.class);

        List<Disease> diseases = diseaseMapper.selectAll();

        sqlSession.close();
        return diseases;
    }

    public void add(Disease disease) {
        SqlSession sqlSession = factory.openSession();

        DiseaseMapper diseaseMapper = sqlSession.getMapper(DiseaseMapper.class);

        diseaseMapper.add(disease);

        sqlSession.commit();

        sqlSession.close();
    }

    public List<Disease> selectByCropDiseaseName(Disease disease) {
        SqlSession sqlSession = factory.openSession();

        DiseaseMapper diseaseMapper = sqlSession.getMapper(DiseaseMapper.class);

        List<Disease> diseases = diseaseMapper.selectByCropDiseaseName(disease);

        sqlSession.close();
        return diseases;
    }

    public void deleteByids(int[] ids) {
        SqlSession sqlSession = factory.openSession();

        DiseaseMapper diseaseMapper = sqlSession.getMapper(DiseaseMapper.class);

        diseaseMapper.deleteByIds(ids);

        sqlSession.commit();//切记
        sqlSession.close();
    }

    public PageBean<Disease> selectByPage(int currentPage, int pageSize) {
        SqlSession sqlSession = factory.openSession();

        DiseaseMapper diseaseMapper = sqlSession.getMapper(DiseaseMapper.class);

        //计算开始索引
        int begin=(currentPage-1)*pageSize;
        int size=pageSize;

        List<Disease> rows = diseaseMapper.selectByPage(begin, size);
        int totalCount = diseaseMapper.selectTotalCount();

        //封装PageBean对象
        PageBean<Disease> pageBean = new PageBean<Disease>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        sqlSession.close();
        return pageBean;
    }

    public PageBean<Disease> selectByPageAndCondition(int currentPage, int pageSize,Disease disease) {
        SqlSession sqlSession = factory.openSession();

        DiseaseMapper diseaseMapper = sqlSession.getMapper(DiseaseMapper.class);

        //计算开始索引
        int begin=(currentPage-1)*pageSize;
        int size=pageSize;

        List<Disease> rows = diseaseMapper.selectByPageAndCondition(begin, size, disease);
        int totalCount = diseaseMapper.selectTotalCountByCondition(disease);

        //封装PageBean对象
        PageBean<Disease> pageBean = new PageBean<Disease>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        sqlSession.close();
        return pageBean;
    }
}
