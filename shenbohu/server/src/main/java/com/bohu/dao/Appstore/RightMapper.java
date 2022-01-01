package com.bohu.dao.Appstore;

import com.bohu.pojo.Right;
import com.bohu.vo.RightVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RightMapper {
    int deleteByPrimaryKey(String rightid);

    int insert(Right record);

    int insertSelective(Right record);

    Right selectByPrimaryKey(String rightid);

    int updateByPrimaryKeySelective(Right record);

    int updateByPrimaryKey(Right record);

    List<RightVO> selectByIds(@Param("roleids") List<String> roleids);

}