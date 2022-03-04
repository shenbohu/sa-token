package com.bohu.service.impl;


import com.bohu.entity.MessageConstant;
import com.bohu.entity.Result;
import com.bohu.service.WeixinPayService;
import com.bohu.utils.HttpClient;
import com.bohu.utils.MatrixToImageWriterWithLogo;
import com.bohu.utils.StatusCode;
import com.github.wxpay.sdk.WXPayUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou.pay.service.impl *
 * @since 1.0
 */
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${weixin.appid}")
    private String appid;


    @Value("${weixin.partner}")
    private String partner;


    @Value("${weixin.partnerkey}")
    private String partnerkey;
    @Value("${weixin.notifyurl}")
    private String notifyurl;


    /**
     * 使用httpclient 模拟浏览器 调用微信的统一下单的API(接口)发送请求(获取code_url)
     *
     * @param out_trade_no
     * @param total_fee
     * @return
     */
    @Override
    public Result createNative(String out_trade_no, String total_fee,
                               HttpServletResponse response,
                               HttpServletRequest request,
                               HttpSession session) {
        try {

            //Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);


            //1.创建参数对象(map) 用于组合参数
            Map<String, String> paramMap = new HashMap<>();
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip.indexOf(",") != -1) {
                String[] ips = ip.split(",");
                ip = ips[0].trim();
            }
            //2.设置参数值(根据文档来写)
            paramMap.put("appid", appid);
            paramMap.put("mch_id", partner);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paramMap.put("body", "博虎");
            paramMap.put("out_trade_no", out_trade_no);
            paramMap.put("total_fee", total_fee);//单位是分
            paramMap.put("spbill_create_ip", ip);//终端的IP
            paramMap.put("notify_url", notifyurl);
            paramMap.put("trade_type", "NATIVE");//扫码支付类型
            //设置签名(不做,转换的时候自动添加签名)
            //3.转成XML 字符串 自动签名
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, partnerkey);
            //4.创建httpclient对象(模拟浏览器)
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //5.设置https协议
            httpClient.setHttps(true);
            //6.设置请求体
            httpClient.setXmlParam(xmlParam);
            //7.发送请求
            httpClient.post();
            //8.获取微信支付系统返回的响应结果(XML格式的字符串)
            String content = httpClient.getContent();
            //9.转成Map  返回
            Map<String, String> allMap = WXPayUtil.xmlToMap(content);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("out_trade_no", out_trade_no);
            resultMap.put("total_fee", total_fee);
            resultMap.put("code_url", allMap.get("code_url"));
            if (Objects.equals("SUCCESS", allMap.get("return_code"))) {
                File file = ResourceUtils.getFile("classpath:document/weixin.jpeg");
                String absolutePath = file.getAbsolutePath();
                if (StringUtils.isEmpty(allMap.get("code_url"))) {
                    return new Result(false, StatusCode.ERROR, MessageConstant.DELETE_CHECKITEM_ORDER);
                }
                BufferedImage bufferedImage = MatrixToImageWriterWithLogo.genBarcode(allMap.get("code_url"),
                        350, 350, absolutePath); // 二维码的内容，宽，高，二维码中心的图片地址
                ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
                return new Result(true, StatusCode.OK, resultMap);
            } else {
                session.setAttribute("create_wx_qrcode_error_msg", resultMap.get("return_msg"));
                return new Result(false, StatusCode.ERROR, MessageConstant.DELETE_CHECKITEM_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, StatusCode.ERROR, MessageConstant.DELETE_CHECKITEM_FAIL);
    }

    @Override
    public Map<String, String> queryStatus(String out_trade_no) {
        try {
            //1.创建参数对象(map) 用于组合参数

            Map<String, String> paramMap = new HashMap<>();

            //2.设置参数值(根据文档来写)
            paramMap.put("appid", appid);
            paramMap.put("mch_id", partner);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paramMap.put("out_trade_no", out_trade_no);

            //设置签名(不做,转换的时候自动添加签名)


            //3.转成XML 字符串 自动签名
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, partnerkey);

            //4.创建httpclient对象(模拟浏览器)
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");

            //5.设置https协议
            httpClient.setHttps(true);

            //6.设置请求体
            httpClient.setXmlParam(xmlParam);

            //7.发送请求
            httpClient.post();

            //8.获取微信支付系统返回的响应结果(XML格式的字符串)

            String content = httpClient.getContent();

            System.out.println(content);

            //9.转成Map  返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
