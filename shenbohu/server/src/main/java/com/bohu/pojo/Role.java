package com.bohu.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * role
 * @author 
 */
@Data
public class Role implements Serializable {
    /**
     * 角色id
     */
    private String roleid;

    private Date cdate;

    private Date udate;

    /**
     * 创建人
     */
    private String founder;

    /**
     * 描述
     */
    private String description;

    /**
     * 名称
     */
    private String name;


    /**
     * 标识
     */
    private String logo;



    private static final long serialVersionUID = 1L;
}