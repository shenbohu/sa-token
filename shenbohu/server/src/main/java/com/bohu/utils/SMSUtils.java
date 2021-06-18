package com.bohu.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.netflix.client.ClientException;

/**
 * @ClassName SMSUtils
 * @Author shenbohu
 * @Date 2021/6/139:57 下午
 * @Version 1.0
 **/
public class SMSUtils {
    public static final String VALIDATE_CODE = "SMS_175061136";//发送短信验证码
    public static final String ORDER_NOTICE = "SMS_175051399";
    //........

    /**
     * 发送短信
     *
     * @param phoneNumbers
     * @param param
     * @throws ClientException
     */
    public static boolean sendShortMessage(String templateCode, String phoneNumbers, String param ,String SignName) {
        // 设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        final String product = "Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";

        final String accessKeyId = "你的";// 你的accessKeyId
        final String accessKeySecret = "你的";// 你的accessKeySecret
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(phoneNumbers);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(SignName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + param + "\"}");
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
        }
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            // 请求成功
            System.out.println("请求成功");
            return true;
        }
        return false;
    }
}
