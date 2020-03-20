package com.springmvc.dao;

import com.springmvc.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentDao {

    Student findStudent(@Param("username") String username);

    List<Student> getStudentList(Map<String,Object> queryMap);

    Integer getTotal(Map<String,Object> queryMap);

    Integer addStudent(Student student);

    Boolean editStudent(Student student);

    Boolean deleteStudentById(@Param("ids") Integer[] ids);
}
