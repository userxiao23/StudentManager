package com.springmvc.service;

import com.springmvc.dao.ClazzDao;
import com.springmvc.entity.Clazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ Author     : wz
 * @ ClassName  : ClazzService
 * @ Date       ：Created in 2019/11/14 15:06
 * @ Description：
 * @ Modified By：
 **/
@Service
public class ClazzService {

    @Autowired
    ClazzDao clazzDao;

    /**
     * 获取所有班级信息
     * @param
     * @return
     */
    public List<Clazz> findClazzAll(){
        List<Clazz> clazzList = clazzDao.findClazzAll();
        return clazzList;
    }

    /**
     * 获取班级列表
     * @param queryMap
     * @return
     */
    public List<Clazz> getClazzList(Map<String,Object> queryMap){
        List<Clazz> clazzList = clazzDao.getClazzList(queryMap);
        return clazzList;
    }

    /**
     * 查询班级总数
     */
    public Integer getTotal(Map<String,Object> queryMap){
        Integer total = clazzDao.getTotal(queryMap);
        return total;
    }

    /**
     * 添加班级
     */
    public Integer addClazz(Clazz clazz){
        Integer count = clazzDao.addClazz(clazz);
        return count;
    }
    /**
     * 修改班级信息
     */
    public Boolean edit(Clazz clazz){
        Boolean flag = clazzDao.edit(clazz);
        return flag;
    }
    /**
     * 批量删除班级
     */
    public Boolean deleteClazzById(Integer[] ids){

        Boolean flag = clazzDao.deleteClazzById(ids);
        return flag;
    }
}
