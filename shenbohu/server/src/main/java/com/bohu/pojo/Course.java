package com.bohu.pojo;

import lombok.Data;

/**
 * @ClassName Course
 * @Author shenbohu
 * @Date 2022/1/28:57 PM
 * @Version 1.0
 **/
@Data
public class   Course {

    private Long cid;
    private String cname;
    private Long userId;
    private String status;
}
