package com.springmvc.controller;

import com.springmvc.entity.Student;
import com.springmvc.entity.User;
import com.springmvc.service.StudentService;
import com.springmvc.service.UserService;
import com.springmvc.util.CpachaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     : wz
 * @ ClassName  : SystemController
 * @ Date       ：Created in 2019/11/12 12:38
 * @ Description：
 * @ Modified By：
 **/
@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;

    /**
     * 进入学生管理系统默认页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model){
        model.setViewName("system/index");
        return model;
    }

    /**
     * 进入登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView model){
        model.setViewName("system/login");
        return model;
    }

    /**
     * 登录表单提交
     * @param username
     * @param password
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> login(
            @RequestParam(value = "username",required = false) String username,
            @RequestParam(value = "password",required = false) String password,
            @RequestParam(value = "type",required = false) Integer type,
            @RequestParam(value = "vcode",required = false) String vcode,
            HttpSession session){
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isEmpty(username)){
            map.put("type","error");
            map.put("msg","用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)){
            map.put("type","error");
            map.put("msg","密码不能为空");
            return map;
        }
        if (StringUtils.isEmpty(vcode)){
            map.put("type","error");
            map.put("msg","验证码不能为空");
            return map;
        }
        /**
         * 验证码校验
         */
        System.out.println(session.getAttribute("loginCpacha"));
        String loginCpache =(String)session.getAttribute("loginCpacha");
        if (StringUtils.isEmpty(loginCpache)){
            map.put("type","error");
            map.put("msg","长时间未操作会话已失效，请刷新后重试！");
            return map;
        }
        //验证码都转换成大写进行比较
        if (! vcode.toUpperCase().equals(loginCpache.toUpperCase())){
            map.put("type", "error");
            map.put("msg","验证码错误");
            return map;
        }
        session.setAttribute("loginCpacha",null);
        if (type == 1){
            User user = userService.findUser(username);

            if (user == null){
                map.put("type", "error");
                map.put("msg","该用户不存在");
                return map;
            }

            if (!password.equals(user.getPassword())){
                map.put("type", "error");
                map.put("msg","密码错误");
                return map;
            }
            session.setAttribute("user",user);
            session.setAttribute("type",type);
        }
        if (type == 2){
            Student student = studentService.findStudent(username);
            if (student.getUsername() == null){
                map.put("type", "error");
                map.put("msg","该学生不存在");
                return map;
            }

            if (!password.equals(student.getPassword())){
                map.put("type", "error");
                map.put("msg","密码错误");
                return map;
            }
            session.setAttribute("user",student);
            session.setAttribute("type",type);

        }
        session.setAttribute("userType",type);
        map.put("type", "success");
        map.put("msg", "登录成功");
        return map;

    }

    /**
     * 登录页面生成验证码
     */
    @RequestMapping(value = "/getVerify", method = RequestMethod.GET)
    public void getVerify(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(value = "vl",defaultValue = "4",required = false) Integer vl,
                          @RequestParam(value = "w",defaultValue = "98",required = false) Integer w,
                          @RequestParam(value = "h",defaultValue = "33",required = false) Integer h){
        CpachaUtil cpachaUtil = new CpachaUtil(vl,w,h);
        String generatorVCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute("loginCpacha",generatorVCode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
        try {
            ImageIO.write(generatorRotateVCodeImage,"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
