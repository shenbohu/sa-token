package com.bohu.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.bohu.entity.R;
import com.bohu.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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
public class FileUploadController {

    /**
     * 上传文件
     *
     * @return
     */
    @PostMapping("upload")
    public R uploadTeacherImg(@RequestParam("file") MultipartFile file) {
        // 地域节点
        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.KEYID;
        String accessKeySecret = ConstantPropertiesUtil.KEYSECRET;
        // BucketName
        String yourBucketName = ConstantPropertiesUtil.BUCKETNAME;

        try {
            // 1.获取上传文件 MultipartFile file
            // @RequestParam("file") file 与表单输入项的name值保持一致

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

            return R.ok().data("imgurl", path);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
