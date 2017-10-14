package com.singsong.erp.base.constants;

/**
 * 一些基础常量设置
 * @author 杨树安
 * @version 创建时间：2017年9月26日 下午9:19:55
 */
public class Constants {

    public static final String LOGIN_USER_ATTRIBUTE_KEY     = "loginUser";

    public static final String SESSION_USER_KEY             = "SESSION_USER_KEY";

    public static final String SESSION_DBMENUMAP_KEY        = "SESSION_DBMENUMAP_KEY";
    
    public static final String COOKIE_LOGIN_USERNAME="sessionLoginUserName";
    
    public static final String COOKIE_LOGIN_PASSWORD="sessionLoginPassword";

    public static final int    COOKIE_USERNAME_INVALID_TIME = 60 * 60 * 24 * 14;      //两周

    public static final int    COOKIE_PASSWORD_INVALID_TIME = 60 * 60 * 24 * 7;       //一周

    public static final String LOGIN_URL                    = "/common/login";

    public static final String NO_PERMISS_URL               = "/common/noPermiss";

    public static final String INDEX_URL                    = "/index";

    public static final int    DELETE_FLAG_TRUE             = 1;                      //已删除

    public static final int    DELETE_FLAG_FALSE            = 0;                      //未删除,默认值
    
    public static final String MENU_TREE        = "menutree";

}
