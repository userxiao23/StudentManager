package com.springmvc.dao;

import com.springmvc.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User findUser(@Param("username") String username);

    List<User> getUserList(Map<String,Object> queryMap);

    Integer getTotal(Map<String,Object> queryMap);

    Integer addUser(User user);

    Boolean edit(User user);

    Boolean deleteUserById(@Param("ids") Integer[] ids);

}
