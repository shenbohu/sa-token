package com.bohu.config;

import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//读写分离
@Configuration
@MapperScan(basePackages = "com.bohu.dao.Appstore",sqlSessionTemplateRef = "AppstoreSqlSessionTemplate")
public class AppstoreSharding {

    @Bean(name="a1")
    @ConfigurationProperties(prefix = "spring.datasource.appstore")
    public DataSource appstoreDataSource()  {
        return DataSourceBuilder.create().build() ;
    }

    @Bean(name="a2")
    @ConfigurationProperties(prefix = "spring.datasource.appstore1")
    public DataSource appstoreDataSource1()  {
        return DataSourceBuilder.create().build() ;
    }



    //创建数据源，需要把分库的库都传递进去
    @Bean("AppstoredataSource")
    public DataSource dataSource(@Qualifier("a1") DataSource a1,
                                 @Qualifier("a2") DataSource a2) throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
        dataSourceMap.put("a1", a1);
        dataSourceMap.put("a2", a2);
        //参数：名称 无意义   主库 ，从库
        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration(
                "a1","a1", Arrays.asList("a2"));
        // 获取数据源对象
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfiguration, new Properties());
        return dataSource;
    }




    @Bean(name="AppstoreSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory (@Qualifier("AppstoredataSource") DataSource dataSource) throws  Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(new DataSourceProxy(dataSource));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/Appstore/*.xml"));
        return sqlSessionFactoryBean.getObject();

    }


    @Bean(name="AppstoreTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("AppstoredataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean(name="AppstoreSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("AppstoreSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }





}
