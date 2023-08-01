package com.beixian.Calculator.util;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: 贝先 [ Cason mo ]
 * Date: 2023/8/1
 * Time: 16:26
 */
public class ValidateUtil {
    /**
     * 正则判断是否为正整数
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
