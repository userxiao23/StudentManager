package com.springmvc.service;

import com.springmvc.dao.StudentDao;
import com.springmvc.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ Author     : wz
 * @ ClassName  : StudentService
 * @ Date       ：Created in 2019/11/12 12:39
 * @ Description：
 * @ Modified By：
 **/
@Service
public class StudentService {

    @Autowired
    StudentDao studentDao;

    /**
     * 获取登录学生信息
     * @param username
     * @return
     */
    public Student findStudent(@Param("username") String username){
        Student student = studentDao.findStudent(username);
        return student;
    }

    /**
     * 获取学生列表
     * @param queryMap
     * @return
     */
    public List<Student> getStudentList(Map<String,Object> queryMap){
        List<Student> studentList = studentDao.getStudentList(queryMap);
        return studentList;
    }

    /**
     * 查询学生总数
     */
    public Integer getTotal(Map<String,Object> queryMap){
        Integer total = studentDao.getTotal(queryMap);
        return total;
    }

    /**
     * 添加学生
     */
    public Integer addStudent(Student student){
        Integer count = studentDao.addStudent(student);
        return count;
    }
    /**
     * 修改学生信息
     */
    public Boolean editStudent(Student student){
        Boolean flag = studentDao.editStudent(student);
        return flag;
    }
    /**
     * 批量删除学生
     */
    public Boolean deleteStudentById(Integer[] ids){

        Boolean flag = studentDao.deleteStudentById(ids);
        return flag;
    }
}
