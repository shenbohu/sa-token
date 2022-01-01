package com.bohu.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * order
 * @author 
 */
@Data
public class Order implements Serializable {
    private String oid;

    private String type;

    private static final long serialVersionUID = 1L;
}