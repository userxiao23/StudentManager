package com.springmvc.service;

import com.springmvc.dao.GradeDao;
import com.springmvc.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ Author     : wz
 * @ ClassName  : GradeService
 * @ Date       ：Created in 2019/11/14 13:53
 * @ Description：
 * @ Modified By：
 **/
@Service
public class GradeService {

    @Autowired
    GradeDao gradeDao;

    /**
     * 获取所有年级信息
     * @param
     * @return
     */
    public List<Grade> findGradeAll(){
        List<Grade> gradeAll = gradeDao.findGradeAll();
        return gradeAll;
    }

    /**
     * 获取年级列表
     * @param queryMap
     * @return
     */
    public List<Grade> getGradeList(Map<String,Object> queryMap){
        List<Grade> gradeList = gradeDao.getGradeList(queryMap);
        return gradeList;
    }

    /**
     * 查询年级总数
     */
    public Integer getTotal(Map<String,Object> queryMap){
        Integer total = gradeDao.getTotal(queryMap);
        return total;
    }

    /**
     * 添加年级
     */
    public Integer addGrade(Grade grade){
        Integer count = gradeDao.addGrade(grade);
        return count;
    }
    /**
     * 修改年级信息
     */
    public Boolean edit(Grade grade){
        Boolean flag = gradeDao.edit(grade);
        return flag;
    }
    /**
     * 批量删除年级
     */
    public Boolean deleteGradeById(Integer[] ids){

        Boolean flag = gradeDao.deleteGradeById(ids);
        return flag;
    }
}
