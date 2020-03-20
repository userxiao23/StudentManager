package com.springmvc.controller;

import com.springmvc.entity.Clazz;
import com.springmvc.entity.Grade;
import com.springmvc.entity.Page;
import com.springmvc.service.ClazzService;
import com.springmvc.service.GradeService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     : wz
 * @ ClassName  : ClazzController
 * @ Date       ：Created in 2019/11/14 14:58
 * @ Description：
 * @ Modified By：
 **/
@RestController
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    ClazzService clazzService;
    @Autowired
    GradeService gradeService;

    /**
     * 班级管理页
     * @param model
     * @return
     */
    @RequestMapping(value = "/clazzList",method = RequestMethod.GET)
    public ModelAndView clazzList(ModelAndView model){
        model.setViewName("clazz/clazzList");
        List<Grade> gradeAll = gradeService.findGradeAll();
        model.addObject("gradeList",gradeAll);
        model.addObject("gradeListJson",JSONArray.fromObject(gradeAll));
        return model;
    }

    /**
     * 查询班级列表
     * @param name
     * @param page
     * @return
     */
    @RequestMapping(value = "/getClazzList",method = RequestMethod.POST)
    public Map<String,Object> getClazzList(@RequestParam(value = "name",required = false) String name,
                                           @RequestParam(value = "gradeId",required = false) Integer gradeId,
                                           Page page){//前端传给后端page属性
        Map<String,Object> queryMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        //根据班级名称和所属的年级进行模糊查询
        queryMap.put("name",name);
        queryMap.put("gradeId",gradeId);

        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());//将page属性传给后端进行真分页查询
        map.put("rows",clazzService.getClazzList(queryMap));
        map.put("total",clazzService.getTotal(queryMap));
        return map;
    }

    /**
     * 添加班级
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/addClazz",method = RequestMethod.POST)
    public Map<String,String> addClazz(Clazz clazz){
        Map<String,String> map = new HashMap<>();
        Integer count = clazzService.addClazz(clazz);
        if (StringUtils.isEmpty(clazz.getName())){
            map.put("type","error");
            map.put("msg","请填写班级名称");
            return map;
        }
        if (count <= 0){
            map.put("type","error");
            map.put("msg","班级添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","班级信息添加成功");
        return map;
    }

    @RequestMapping(value = "/editClazz",method = RequestMethod.POST)
    public Map<String,String> editClazz(Clazz clazz){
        Map<String,String> map = new HashMap<>();

        if (StringUtils.isEmpty(clazz.getName())){
            map.put("type","error");
            map.put("msg","请填写班级名称");
            return map;
        }
        Boolean flag = clazzService.edit(clazz);
        if (flag == false){
            map.put("type","error");
            map.put("msg","班级修改失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","班级信息修改成功");
        return map;
    }

    @RequestMapping(value = "/deleteClazzById",method = RequestMethod.POST)
    public Map<String,String> deleteClazzById(@RequestParam(value = "ids[]",required = false) Integer[] ids){
        Map<String,String> map = new HashMap<>();

        if (ids == null){
            map.put("type","error");
            map.put("msg","请选择要删除的数据");
        }
        try {
            Boolean flag = clazzService.deleteClazzById(ids);
            if (flag == false){
                map.put("type","error");
                map.put("msg","删除失败");
                return map;
            }
        } catch (Exception e) {
            map.put("type","error");
            map.put("msg","该班级下存在学生，请勿冲动");
            return map;
        }
        map.put("type","success");
        map.put("msg","删除成功");
        return map;
    }
}
