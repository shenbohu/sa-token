package com.bohu.dao.Business;



import com.bohu.pojo.Cordits;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CorditsMapper {

    int insert(Cordits cordits);

}
