package com.bohu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName User
 * @Author shenbohu
 * @Date 2021/5/1910:34 上午
 * @Version 1.0
 **/
@Table(name="user")
@Data
public class User implements Serializable {
    @Id
    private String username;//用户名
    private String password;//密码，加密存储
    private String phone;//注册手机号
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "yyyy-MM-dd HH-mm-ss" ,timezone = "GMT-8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private java.util.Date created;//创建时间
    private java.util.Date updated;//修改时间
    private String name;//真实姓名
    private String status;//使用状态（1正常 0非正常）
    private String ismobilecheck;//手机是否验证 （0否  1是）
    private String sex;//性别，1男，0女
    private java.util.Date birthday;//出生年月日
    private java.util.Date lastlogintime;//最后登录时间

}
