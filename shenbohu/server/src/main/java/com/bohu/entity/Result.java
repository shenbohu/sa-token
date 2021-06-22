package com.bohu.entity;

import com.bohu.utils.StatusCode;
import lombok.Data;

import java.util.Objects;

/**
 * 返回结果实体类
 */
@Data
public class Result<T> {

    private boolean flag;//是否成功
    private Integer code;//返回码
    private String message;//返回消息
    private ResultEnum resultEnum;

    private T data;//返回数据

    public Result(ResultEnum enumValue, Object data) {
        this.flag = enumValue.getType();
        this.code = enumValue.getCode();
        this.message = enumValue.getMessage();
        this.data = (T) data;
    }

    public static Result ok(Object data) {
        Result r = new Result();
        r.setFlag(true);
        r.setCode(StatusCode.OK);
        r.setMessage(MessageConstant.OK);
        r.setData(data);
        return r;
    }

    public static Result ok() {
        Result r = new Result();
        r.setFlag(true);
        r.setCode(StatusCode.OK);
        r.setMessage(MessageConstant.OK);
        return r;
    }

    public static Result error(Object data) {
        Result r = new Result();
        r.setFlag(true);
        r.setCode(StatusCode.ERROR);
        r.setMessage(MessageConstant.ERROR);
        r.setData(data);
        return r;
    }

    public static Result error() {
        Result r = new Result();
        r.setFlag(true);
        r.setCode(StatusCode.ERROR);
        r.setMessage(MessageConstant.ERROR);
        return r;
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = (T) data;
    }

    public Result(ResultEnum enumValue) {
        this.flag = enumValue.getType();
        this.code = enumValue.getCode();
        this.message = enumValue.getMessage();
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

    public Result(boolean flag, Integer code, Object data) {
        this.flag = flag;
        this.code = code;
        this.data = (T) data;
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
