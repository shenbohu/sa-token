package com.bohu.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_user0
 * @author 
 */
@Data
public class TUser implements Serializable {
    /**
     * 主键
     */
    private Long userId;

    /**
     * 姓名
     */
    private String name;

    private static final long serialVersionUID = 1L;
}