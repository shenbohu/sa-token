package com.bohu.service;

import com.bohu.entity.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.pay.service *
 * @since 1.0
 */
public interface WeixinPayService {
    /**
     * 生成二维码
     *
     * @param out_trade_no
     * @param total_fee
     * @return
     */
    Result createNative(String out_trade_no, String total_fee,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        HttpSession session);

    /**
     * @param out_trade_no
     * @return
     */
    Map<String, String> queryStatus(String out_trade_no);
}
