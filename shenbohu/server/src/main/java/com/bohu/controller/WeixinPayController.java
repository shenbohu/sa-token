package com.bohu.controller;


import com.alibaba.fastjson.JSON;


import com.bohu.entity.Result;
import com.bohu.service.WeixinPayService;
import com.bohu.utils.StatusCode;
import com.github.wxpay.sdk.WXPayUtil;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/weixin/pay")
public class WeixinPayController {


    @Autowired
    private WeixinPayService weixinPayService;


    /**
     * 创建二维码连接地址返回给前端 生成二维码图片
     *
     * @param out_trade_no
     * @param total_fee
     * @return
     */
    @RequestMapping("/create/native")
    public Result<Map> createNative(String out_trade_no, String total_fee,
                                    HttpServletResponse response,
                                    HttpServletRequest request,
                                    HttpSession session) {
        return weixinPayService.createNative(out_trade_no, total_fee,
                response, request, session);
    }

    /**
     * 根据交易订单号 来查询订单的状态
     *
     * @param out_trade_no
     * @return
     */
    @RequestMapping("/status/query")
    public Result<Map> queryStatus(String out_trade_no) {
        Map<String, String> resultMap = weixinPayService.queryStatus(out_trade_no);
        return new Result<Map>(true, StatusCode.OK, "查询状态OK", resultMap);
    }

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    /**
     * 接收 微信支付通知的结果  结果(以流的形式传递过来)
     */
    @RequestMapping("/notify/url")
    public String jieshouResult(HttpServletRequest request) {

        try {

            //1.获取流信息
            ServletInputStream ins = request.getInputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();


            //todo
            byte[] buffer = new byte[1024];
            int len;
            while ((len = ins.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }

            bos.close();
            ins.close();

            //2.转换成XML字符串
            byte[] bytes = bos.toByteArray();

            //微信支付系统传递过来的XML的字符串
            String resultStrXML = new String(bytes, "utf-8");
            //3.转成MAP
            Map<String, String> map = WXPayUtil.xmlToMap(resultStrXML);

            System.out.println(resultStrXML);

            //4.发送消息给Rabbitmq  .........
            String data = JSON.toJSONString(map);
//            rabbitTemplate.convertAndSend(env.getProperty("mq.pay.exchange.order"),env.getProperty("mq.pay.routing.key"),data);

            //5.返回微信的接收请况(XML的字符串)

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("return_code", "SUCCESS");
            resultMap.put("return_msg", "OK");
            return WXPayUtil.mapToXml(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
