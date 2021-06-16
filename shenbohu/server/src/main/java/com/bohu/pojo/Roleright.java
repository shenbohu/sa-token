package com.bohu.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * roleright
 * @author 
 */
@Data
public class Roleright extends RolerightKey implements Serializable {
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

    /**
     * 增加
     */
    private String c;

    /**
     * 查询
     */
    private String r;

    /**
     * 修改
     */
    private String u;

    /**
     * 删除
     */
    private String d;

    private static final long serialVersionUID = 1L;
}