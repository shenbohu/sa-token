package com.bohu.service.impl;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.bohu.config.ShardingJdbcConfig;
import com.bohu.dao.Appstore.OrderMapper;
import com.bohu.dao.Appstore.RightMapper;
import com.bohu.dao.Appstore.RoleMapper;
import com.bohu.dao.Appstore.UserMapper;
import com.bohu.dao.Business.CorditsMapper;
import com.bohu.dao.Sharding.CourseMapper;
import com.bohu.entity.PageResult;
import com.bohu.entity.Result;
import com.bohu.entity.YmlConfig;
import com.bohu.feign.corditsfeign;
import com.bohu.pojo.Cordits;
import com.bohu.pojo.Course;
import com.bohu.pojo.Role;
import com.bohu.pojo.User;
import com.bohu.service.CourseService;
import com.bohu.service.UserService;
import com.bohu.utils.SMSUtils;
import com.bohu.utils.StatusCode;
import com.bohu.utils.ValidateCodeUtils;
import com.bohu.vo.RightVO;
import com.bohu.vo.UserVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName UserServiceImpl
 * @Author shenbohu
 * @Date 2021/5/1910:20 上午
 * @Version 1.0
 **/
@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Autowired
    private YmlConfig ymlConfig;


    @Override
    public PageResult getallcourse(String pageNum, String pageSize) {
        List<Course> courses = new ArrayList<>();
        HintManager hintManager = HintManager.getInstance() ;
        hintManager.setMasterRouteOnly();

        //courses = courseMapper.selectByMap(null);

       // courses = courseMapper.selectAll();
        //Course course = courseMapper.selectByPrimaryKey(684693663745310720L);

//        ShardingJdbcConfig shardingJdbcConfig = new ShardingJdbcConfig();
//        QueryWrapper<Course> wrapper = new QueryWrapper<>();
//        PageHelper pageHelper = shardingJdbcConfig.getPageHelper();
//        Page page = pageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));


        PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));

        courses = courseMapper.selectAll();
        PageInfo pageInfo = new PageInfo(courses);

        return new PageResult(pageInfo.getTotal(), courses);
    }


}
