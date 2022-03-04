package com.bohu.dao.Sharding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bohu.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;
//import tk.mybatis.mapper.common.BaseMapper;

@Repository
@MapperScan("com.bohu.dao.AppstoreSharding")
//@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    int deleteByPrimaryKey(Long cid);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Long cid);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> selectAll();

}