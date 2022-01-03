package com.bohu.config;

import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @ClassName AppstoreDataSourceConfig
 * @Author shenbohu
 * @Date 2021/12/292:33 PM
 * @Version 1.0
 **/
@Configuration
@MapperScan(basePackages = "com.bohu.dao.Business",sqlSessionTemplateRef = "businessSqlSessionTemplate")
public class BusinessDataSourceConfig {

    @Bean(name="businessDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.business")
    public DataSource businessDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name="businessSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory (@Qualifier("businessDataSource") DataSource dataSource) throws  Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(new DataSourceProxy(dataSource));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.bohu.pojo");
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactory;

    }


    @Bean(name="businessTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("businessDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name="businessSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("businessSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
