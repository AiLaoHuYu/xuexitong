package com.qq.xuexitong.utils

import java.util.regex.Pattern

object PatternUtil {

    //手机号正则表达式
    const val PHONE = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57]|19[89]|166)[0-9]{8}"


    /**
     * 正则表达式匹配判断
     * @param patternStr 匹配规则
     * @param input 需要做匹配操作的字符串
     * @return true if matched, else false
     */
    //此处传入两个值，第一个是手机号的正则表达式，第二个是输入的手机号
    fun isMatched(patternStr: String, input: CharSequence): Boolean {
        val pattern = Pattern.compile(patternStr);
        val matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

}