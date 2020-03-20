package com.springmvc.service;

import com.springmvc.dao.UserDao;
import com.springmvc.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ Author     : wz
 * @ ClassName  : UserService
 * @ Date       ：Created in 2019/11/12 12:39
 * @ Description：
 * @ Modified By：
 **/
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    /**
     * 获取登录用户信息
     * @param username
     * @return
     */
    public User findUser(@Param("username") String username){
        User user = userDao.findUser(username);
        return user;
    }

    /**
     * 获取用户列表
     * @param queryMap
     * @return
     */
    public List<User> getUserList(Map<String,Object> queryMap){
        List<User> userList = userDao.getUserList(queryMap);
        return userList;
    }

    /**
     * 查询用户总数
     */
    public Integer getTotal(Map<String,Object> queryMap){
        Integer total = userDao.getTotal(queryMap);
        return total;
    }

    /**
     * 添加用户
     */
    public Integer addUser(User user){
        Integer count = userDao.addUser(user);
        return count;
    }
    /**
     * 修改用户信息
     */
    public Boolean edit(User user){
        Boolean flag = userDao.edit(user);
        return flag;
    }
    /**
     * 批量删除用户
     */
    public Boolean deleteUserById(Integer[] ids){

        Boolean flag = userDao.deleteUserById(ids);
        return flag;
    }
}
