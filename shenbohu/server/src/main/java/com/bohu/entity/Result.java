package com.bohu.entity;

import com.bohu.utils.StatusCode;
import lombok.Data;

/**
 * 返回结果实体类
 */
@Data
public class Result<T> {

    private boolean flag;//是否成功
    private Integer code;//返回码
    private String message;//返回消息

    private T data;//返回数据

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = (T)data;
    }

    public Result(boolean flag, String massage) {
        this.flag = flag;
        this.message = message;

    }
    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(boolean flag ,Integer code ,Object data) {
        this.flag = flag;
        this.code = code;
        this.data = (T)data;
    }

    public Result() {
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = "执行成功";
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
