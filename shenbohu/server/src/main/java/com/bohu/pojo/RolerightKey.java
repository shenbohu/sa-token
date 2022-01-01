package com.bohu.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * roleright
 * @author 
 */
@Data
public class RolerightKey implements Serializable {
    /**
     * 角色id
     */
    private String roleid;

    /**
     * 权限id
     */
    private String rightid;

    private static final long serialVersionUID = 1L;
}