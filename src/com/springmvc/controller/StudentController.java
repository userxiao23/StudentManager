package com.springmvc.controller;

import com.springmvc.util.StringUtil;
import net.sf.json.JSONArray;
import com.springmvc.entity.Clazz;
import com.springmvc.entity.Page;
import com.springmvc.entity.Student;
import com.springmvc.service.ClazzService;
import com.springmvc.service.StudentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     : wz
 * @ ClassName  : StudentController
 * @ Date       ：Created in 2019/11/14 14:59
 * @ Description：
 * @ Modified By：
 **/
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    ClazzService clazzService;


    /**
     * 学生管理页
     * @param model
     * @return
     */
    @RequestMapping(value = "/studentList",method = RequestMethod.GET)
    public ModelAndView studentList(ModelAndView model){
        List<Clazz> clazzList = clazzService.findClazzAll();
        model.addObject("clazzList",clazzList);
        model.addObject("clazzListJson",JSONArray.fromObject(clazzList));
        model.setViewName("student/studentList");
        return model;
    }

    /**
     * 查询学生列表
     * 学生只能查询学生自己的信息
     * @param name
     * @param page
     * @return
     */
    @RequestMapping(value = "/getStudentList",method = RequestMethod.POST)
    public Map<String,Object> getStudentList(@RequestParam(value = "name",required = false) String name,
                                             @RequestParam(value = "clazzId",required = false) Integer clazzId,
                                             Page page,
                                             HttpSession session){//前端传给后端page属性
        Map<String,Object> queryMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();

        queryMap.put("clazzId",clazzId);
        queryMap.put("username",name);
        Object attribute = session.getAttribute("userType");
        if ("2".equals(attribute.toString())){
            Student loginedStudent = (Student)session.getAttribute("user");
            queryMap.put("username",loginedStudent.getUsername());
        }
        queryMap.put("offset",page.getOffset());
        queryMap.put("pageSize",page.getRows());//将page属性传给后端进行真分页查询
        map.put("rows",studentService.getStudentList(queryMap));
        map.put("total",studentService.getTotal(queryMap));
        return map;
    }

    /**
     * 添加学生
     * @param student
     * @return
     */
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public Map<String,String> addStudent(Student student){
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isEmpty(student.getUsername())){
            map.put("type","error");
            map.put("msg","请填写学生姓名");
            return map;
        }
        if (StringUtils.isEmpty(student.getPassword())){
            map.put("type","error");
            map.put("msg","请填写学生密码");
            return map;
        }
        if (student.getClazzId() == null){
            map.put("type","error");
            map.put("msg","请选择学生所在班级");
            return map;
        }
        student.setSn(StringUtil.generateSn("S",""));
        Integer count = studentService.addStudent(student);
        if (count <= 0){
            map.put("type","error");
            map.put("msg","学生信息添加失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","学生信息添加成功");
        return map;
    }

    @RequestMapping(value = "/editStudent",method = RequestMethod.POST)
    public Map<String,String> editStudent(Student student){
        Map<String,String> map = new HashMap<>();

        if (StringUtils.isEmpty(student.getUsername())){
            map.put("type","error");
            map.put("msg","请填写学生姓名");
            return map;
        }
        if (StringUtils.isEmpty(student.getPassword())){
            map.put("type","error");
            map.put("msg","请填写学生密码");
            return map;
        }
        if (student.getClazzId() == null){
            map.put("type","error");
            map.put("msg","请选择学生所在班级");
            return map;
        }
        student.setSn(StringUtil.generateSn("S", ""));
        Boolean flag = studentService.editStudent(student);
        if (flag == false){
            map.put("type","error");
            map.put("msg","学生信息修改失败");
            return map;
        }
        map.put("type","success");
        map.put("msg","学生信息修改成功");
        return map;
    }

    @RequestMapping(value = "/deleteStudentById",method = RequestMethod.POST)
    public Map<String,String> deleteStudentById(@RequestParam(value = "ids[]",required = false) Integer[] ids){
        Map<String,String> map = new HashMap<>();

        if (ids == null){
            map.put("type","error");
            map.put("msg","请选择要删除的数据");
        }
        Boolean flag = studentService.deleteStudentById(ids);
        if (flag == true){
            map.put("type","success");
            map.put("msg","学生删除成功");
        }else {
            map.put("type","error");
            map.put("msg","学生删除失败");
        }
        return map;
    }

    @RequestMapping(value = "/uploadPhoto",method = RequestMethod.POST)
    public Map<String,String> uploadPhoto(MultipartFile photo,
                                          HttpServletRequest request,
                                          HttpServletResponse response)throws IOException {
        Map<String,String> map = new HashMap<>();

        if (photo == null){
            map.put("type","error");
            map.put("msg","请选择文件");
            return map;
        }
        if (photo.getSize() > 10485760){
            map.put("type","error");
            map.put("msg","文件大小超过10M,请上传小于10M的文件！");
            return map;
        }
        //获取文件的后缀
        String suffix = photo.getOriginalFilename()
                .substring(photo.getOriginalFilename().lastIndexOf(".") +1,
                photo.getOriginalFilename().length());
        //判断文件类型是否正确
        if (!"jpg,png,gif,jpeg".contains(suffix.toLowerCase())){
            map.put("type","error");
            map.put("msg","文件格式不正确");
            return map;
        }
        //定义文件保存的位置
        //将图片保存在文件下；将图片以二进制的形式保存在数据库中
        String savePath =  request.getServletContext().getRealPath("/") + "\\upload\\";
        File savePathFile = new File(savePath);
        //上传文件不存在的话，则创造一个文件夹
        if (!savePathFile.exists()){
            savePathFile.mkdir();
        }
        //文件名：时间戳加文件后缀
        String filename = new Date().getTime() + "." + suffix;
        //将文件传到目标文件夹中
        photo.transferTo(new File(savePath + filename));
        map.put("type","success");
        map.put("msg","文件上传成功");
        //返回存储图片的路径
        map.put("src",request.getServletContext().getContextPath() + "/upload/"+filename);
        return map;
    }
}
