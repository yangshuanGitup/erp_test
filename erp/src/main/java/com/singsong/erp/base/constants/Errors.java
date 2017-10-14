package com.singsong.erp.base.constants;

public enum Errors {

    FAILURE(0, "操作失败"), 
    SUCCESS(1, "操作成功"), 
    ERR_SYSTEM(1001, "系统异常"), 
    //用户没有登录直接访问地址,则提示这个错误
    ERR_NO_AUTH(1002, "没有做登录认证"),
    //用户登录后,没有权限,则提示这个错误
    ERR_NO_PERMISS(1003, "无权限操作"),
    ERR_LOGIN(1004, "用户名或密码错误"),
    EMPTY_USER(1005,"用户名不能为空"),
    EMPTY_PASSWORD(1006,"密码不能为空"),
    ERR_PASSWORD(1007,"原密码错误"),
    ERR_UNIQUE_NULL(2001, "关键字段不能为空"),
    ERR_UNIQUE_CONSTRAINT(2002, "违反记录的唯一性"),
    ERR_SAVE_ID(2003, "新增时不会有ID号"),
    ERR_ID_NOTEXIST(2004, "ID记录不存在"),
    ERR_ID_NULL(2005, "在修改时ID不能为空"),
    ERR_FOREIGN_KEY(2006, "记录存在被引用,不能删除"),
    ERR_PARAMS(2007, "请求参数错误"),
    ERR_INVALID_DATA(2008, "存在非法数据"),
    ERR_SIGN_DATA(2009, "数据被窜改");
    private int    code;
    private String message;

    Errors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code + ":" + message;
    }
}
