package com.springmvc.controller;

import com.springmvc.entity.Page;
import com.springmvc.entity.User;
import com.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     : wz
 * @ ClassName  : UserController
 * @ Date       ：Created in 2019/11/13 13:41
 * @ Description：
 * @ Modified By：管理员控制器
 **/
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户管理页
     * @param model
     * @return
     */
    @RequestMapping(value = "/userList",method = RequestMethod.GET)
    public ModelAndView userList(ModelAndView model){
        model.setViewName("user/userList");
        return model;
    }

    /**
     * 查询用户列表
     * @param username
     * @param page
     * @return
     */
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    public Map<String,Object> getUserList(@RequestParam(value = "username",required = false) String username,
                                          Page page){//前端传给后端page属性
        Map<String,Object> queryMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        queryMap.put("username",username);
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());//将page属性传给后端进行真分页查询
        map.put("rows",userService.getUserList(queryMap));
        map.put("total",userService.getTotal(queryMap));
        return map;
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public Map<String,String> addUser(User user){
        Map<String,String> map = new HashMap<>();
        Integer count = userService.addUser(user);
        if (count > 0){
            map.put("type","success");
            map.put("msg","用户添加成功");
        }else {
            map.put("type","error");
            map.put("msg","用户添加失败");
        }
        return map;
    }

    @RequestMapping(value = "/editUser",method = RequestMethod.POST)
    public Map<String,String> editUser(User user){
        Map<String,String> map = new HashMap<>();
        Boolean flag = userService.edit(user);
        if (flag == true){
            map.put("type","success");
            map.put("msg","用户修改成功");
        }else {
            map.put("type","error");
            map.put("msg","用户修改失败");
        }
        return map;
    }

    @RequestMapping(value = "/deleteUserById",method = RequestMethod.POST)
    public Map<String,String> deleteUserById(@RequestParam(value = "ids[]",required = false) Integer[] ids){
        Map<String,String> map = new HashMap<>();

        if (ids == null){
            map.put("type","error");
            map.put("msg","请选择要删除的数据");
        }
        Boolean flag = userService.deleteUserById(ids);
        if (flag == true){
            map.put("type","success");
            map.put("msg","用户删除成功");
        }else {
            map.put("type","error");
            map.put("msg","用户删除失败");
        }
        return map;
    }

}
