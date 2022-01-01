package com.bohu.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * userrole
 * @author 
 */
@Data
public class UserroleKey implements Serializable {
    /**
     * 角色id
     */
    private String roleid;

    /**
     * 用户id
     */
    private String username;

    private static final long serialVersionUID = 1L;
}