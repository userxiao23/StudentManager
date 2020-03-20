package com.springmvc.dao;

import com.springmvc.entity.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GradeDao {

    List<Grade> findGradeAll();

    List<Grade> getGradeList(Map<String,Object> queryMap);

    Integer getTotal(Map<String,Object> queryMap);

    Integer addGrade(Grade grade);

    Boolean edit(Grade grade);

    Boolean deleteGradeById(@Param("ids") Integer[] ids);
}
