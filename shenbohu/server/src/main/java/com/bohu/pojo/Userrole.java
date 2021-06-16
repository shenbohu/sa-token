package com.bohu.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * userrole
 * @author 
 */
@Data
public class Userrole extends UserroleKey implements Serializable {
    /**
     * 创建人
     */
    private String founder;

    /**
     * 创建时间
     */
    private Date cdate;

    /**
     * 更新时间
     */
    private Date udate;

    private static final long serialVersionUID = 1L;
}