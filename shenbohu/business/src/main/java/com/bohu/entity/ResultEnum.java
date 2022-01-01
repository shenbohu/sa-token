package com.bohu.entity;


public enum ResultEnum {

    success(200, "成功", true);


    private int code;

    private String message;

    private Boolean type;

    ResultEnum(int code, String message, Boolean type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }

    private static String getMessage(int code) {
        for (ResultEnum value : ResultEnum.values()) {
            if (value.code == code) {
                return value.message;
            }
        }
        return null;
    }

    private static Boolean gettype(int code) {
        for (ResultEnum value : ResultEnum.values()) {
            if (value.code == code) {
                return true;
            }
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }
}
