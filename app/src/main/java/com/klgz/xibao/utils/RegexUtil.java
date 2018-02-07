package com.klgz.xibao.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangning on 2017/9/24.
 */

public class RegexUtil {

  /**
   * 正则：手机号码
   */
  public static final String REGEX_MOBIL_PHONE_NO = "^[1][3,4,5,7,8,9][0-9]{9}$";
  /**
   * 正则：身份证号码 15 位
   */
  public static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
  /**
   * 正则：身份证号码 18 位
   */
  public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
  /**
   * 正则：邮箱
   */
  public static final String REGEX_EMAIL = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";

  /**
   * 正则：汉字
   */
  public static final String REGEX_ZH = "^[\\u4e00-\\u9fa5]+$";
  /**
   * 正则：QQ 号
   */
  public static final String REGEX_TENCENT_QQ = "^[1-9][0-9]{4,10}$";
  /**
   * 正则：微信
   */
  public static final String REGEX_WE_CHAT="^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$";

  public static boolean matcherRegex(String regex, String text) {
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(text);
    return m.matches();
  }

}
