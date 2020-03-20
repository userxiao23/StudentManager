package com.springmvc.controller;

import com.springmvc.entity.Grade;
import com.springmvc.entity.Page;
import com.springmvc.service.GradeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     : wz
 * @ ClassName  : GradeController
 * @ Date       ：Created in 2019/11/14 13:51
 * @ Description：
 * @ Modified By：
 **/
@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    GradeService gradeService;

    /**
     * 年级管理页
     * @param model
     * @return
     */
    @RequestMapping(value = "/gradeList",method = RequestMethod.GET)
    public ModelAndView gradeList(ModelAndView model){
        model.setViewName("grade/gradeList");
        return model;
    }

    /**
     * 查询年级列表
     * @param name
     * @param page
     * @return
     */
    @RequestMapping(value = "/getGradeList",method = RequestMethod.POST)
    public Map<String,Object> getGradeList(@RequestParam(value = "name",required = false) String name,
                                          Page page){//前端传给后端page属性
        Map<String,Object> queryMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        queryMap.put("name",name);
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());//将page属性传给后端进行真分页查询
        map.put("rows",gradeService.getGradeList(queryMap));
        map.put("total",gradeService.getTotal(queryMap));
        return map;
    }

    /**
     * 添加年级
     * @param grade
     * @return
     */
    @RequestMapping(value = "/addGrade",method = RequestMethod.POST)
    public Map<String,String> addGrade(Grade grade){
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isEmpty(grade.getName())){
            map.put("type","error");
            map.put("msg","请填写年级名称");
            return map;
        }
        Integer count = gradeService.addGrade(grade);
        if (count <= 0){
            map.put("type","error");
            map.put("msg","年级添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","年级信息添加成功");
        return map;
    }

    @RequestMapping(value = "/editGrade",method = RequestMethod.POST)
    public Map<String,String> editGrade(Grade grade){
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isEmpty(grade.getName())){
            map.put("type","error");
            map.put("msg","请填写年级名称");
            return map;
        }
        Boolean flag = gradeService.edit(grade);
        if (flag == false){
            map.put("type","error");
            map.put("msg","年级修改失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","年级信息修改成功");
        return map;
    }

    @RequestMapping(value = "/deleteGradeById",method = RequestMethod.POST)
    public Map<String,String> deleteGradeById(@RequestParam(value = "ids[]",required = false) Integer[] ids){
        Map<String,String> map = new HashMap<>();

        if (ids == null){
            map.put("type","error");
            map.put("msg","请选择要删除的数据");
            return map;
        }
        try {
            Boolean flag = gradeService.deleteGradeById(ids);
            if (flag == false){
                map.put("type","error");
                map.put("msg","删除失败");
                return map;
            }
        } catch (Exception e) {
            map.put("type","error");
            map.put("msg","该年级下存在班级信息，请勿冲动");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }
}
