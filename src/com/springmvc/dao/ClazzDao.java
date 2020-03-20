package com.springmvc.dao;

import com.springmvc.entity.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClazzDao {

    List<Clazz> findClazzAll();

    List<Clazz> getClazzList(Map<String,Object> queryMap);

    Integer getTotal(Map<String,Object> queryMap);

    Integer addClazz(Clazz clazz);

    Boolean edit(Clazz clazz);

    Boolean deleteClazzById(@Param("ids") Integer[] ids);
}
