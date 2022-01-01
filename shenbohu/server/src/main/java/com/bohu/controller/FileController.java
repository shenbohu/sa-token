package com.bohu.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.bohu.entity.R;
import com.bohu.entity.Result;
import com.bohu.utils.ConstantPropertiesUtil;
import com.bohu.utils.POIUtils;
import com.bohu.utils.StatusCode;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName FileUploadController
 * @Author shenbohu
 * @Date 2021/6/201:07 上午
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user/oss")
@CrossOrigin // 解决跨域
public class FileController {

    /**
     * 上传文件
     *
     * @return
     */
    @PostMapping("upload")
    public Result uploadTeacherImg(@RequestParam("file") MultipartFile file) {
        // 地域节点
        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.KEYID;
        String accessKeySecret = ConstantPropertiesUtil.KEYSECRET;
        // BucketName
        String yourBucketName = ConstantPropertiesUtil.BUCKETNAME;

        try {
//             1.获取上传文件 MultipartFile file
//             @RequestParam("file") file 与表单输入项的name值保持一致

            // 2.获取上传文件名称，获取上传文件输入流
            String fileName = file.getOriginalFilename();
            // 在文件名称之前添加uuid值，保证文件名称不重复
            String uuid = UUID.randomUUID().toString();
            fileName = uuid + fileName;

            // 获取当前日期 2020/01/03
            String filePath = new DateTime().toString("yyyy/MM/dd");

            // 拼接文件完整路径 2020/01/03/parker.jpg
            fileName = filePath + "/" + fileName;

            // 获取上传文件输入流
            InputStream in = file.getInputStream();

            // 3.把上传文件存储到阿里云oss里面
            // 创建OSSClient实例。
            OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流
            // 第一个参数时BucketName，第二个参数是文件名称，第三个参数是输入流
            ossClient.putObject(yourBucketName, fileName, in);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 返回上传之后的oss存储路径
            String path = "http://" + yourBucketName + "." + endpoint + "/" + fileName;
            Map mapfile = new HashMap();
            mapfile.put("imgurl", path);
            mapfile.put("fileName", fileName);
            return Result.ok(mapfile);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /***
     * @Description:
     * @Author: shenbohu
     * @Date: 2021/6/20 12:18 下午
     * @Param: [url]
     * @return: 下载文件到本地
     **/
    @PostMapping(value = "/download")
    public Result downloadTeacherImg(@RequestParam("url") String url) throws Exception {
        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        String accessKeyId = ConstantPropertiesUtil.KEYID;
        String accessKeySecret = ConstantPropertiesUtil.KEYSECRET;
        String bucketName = ConstantPropertiesUtil.BUCKETNAME;
        String objectName = url;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String uuid = url.substring(0, url.lastIndexOf("."));
        url = url.substring(url.lastIndexOf("/") + 1);
        if (uuid.length() > 47 && url.length() > 36) {
            url = url.substring(36);
        }
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("/Users/shenbohu/Downloads/" + url));
        ossClient.shutdown();
        return Result.ok();

        //imgurl:http://shenbohu.oss-cn-hangzhou.aliyuncs.com/2021/06/20/65481890-d793-4571-893d-3548b59e236a需求汇总.xlsx"
    }
    
}
