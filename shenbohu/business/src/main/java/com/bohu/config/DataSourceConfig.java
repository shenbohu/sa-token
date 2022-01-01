//package com.bohu.config;
//
///**
// * @ClassName DatasourceConfig
// * @Author shenbohu
// * @Date 2021/12/292:52 PM
// * @Version 1.0
// **/
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * 创建iam库的数据源
// */
//@Configuration
//@MapperScan(basePackages = "com.bohu.mapper",sqlSessionTemplateRef = "businessSqlSessionTemplate")
//public class DataSourceConfig {
//
//    @Bean(name="businessDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.business")
//    public DataSource businessDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name="businessSqlSessionFactory")
//    public SqlSessionFactory businessSqlSessionFactory(@Qualifier("businessDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.bohu.pojo");
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @Bean(name="businessTransactionManager")
//    public DataSourceTransactionManager testTransactionManager(@Qualifier("businessDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name="businessSqlSessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("businessSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//}
//
