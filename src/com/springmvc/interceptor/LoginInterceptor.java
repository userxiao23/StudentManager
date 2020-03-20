package com.springmvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ Author     : wz
 * @ ClassName  : LoginInterceptor
 * @ Date       ：Created in 2019/11/13 10:28
 * @ Description：登录拦截器的配置
 * @ Modified By：
 **/
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String url = httpServletRequest.getRequestURI();

        Object user = httpServletRequest.getSession().getAttribute("user");
        if (url.endsWith("index")){
            if (user == null){
                System.out.println("未登录或登录失败，url = " + url);
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "login");
                return false;
            }
            return true;
        }else {
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
