package com.springmvc.util;

import java.util.Date;

/**
 * @ Author     : wz
 * @ ClassName  : StringUtil
 * @ Date       ：Created in 2019/11/15 16:42
 * @ Description：
 * @ Modified By：
 **/
public class StringUtil {
//    自动生成学号
    public static String generateSn(String prefix, String suffix){
        return prefix + new Date().getTime() + suffix;
    }
}
