package com.bohu.vo;

import com.bohu.pojo.Right;
import lombok.Data;

/**
 * @ClassName RightVO
 * @Author shenbohu
 * @Date 2021/6/144:33 下午
 * @Version 1.0
 **/
@Data
public class RightVO extends Right {
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

    //角色id
    private String roleid;

}
