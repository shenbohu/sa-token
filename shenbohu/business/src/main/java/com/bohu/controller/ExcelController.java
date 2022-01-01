package com.bohu.controller;

import com.bohu.entity.Result;
import com.bohu.utils.StatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/excel")
@CrossOrigin // 解决跨域
public class ExcelController {


    /**
     * @Description:
     * @Author: shenbohu
     * @Date: 2021/6/26 10:30 下午
     * @Param: poi导入
     * @return:
     **/

    @PostMapping("importexcel")
    public Result importExcel(@RequestParam("file.conf") MultipartFile file)  throws Exception{


        return new Result(true, StatusCode.OK, "strings");
    }
}
