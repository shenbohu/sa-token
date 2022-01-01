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
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * 创建iam库的数据源
// */
//@Configuration
//@MapperScan(basePackages = "com.bohu.mapper",sqlSessionTemplateRef = "appstoreSqlSessionTemplate")
//public class DataSourceConfig {
//
//    @Bean(name="appstoreDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.appstore")
//    public DataSource appstoreDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name="appstoreSqlSessionFactory")
//    public SqlSessionFactory appstoreSqlSessionFactory(@Qualifier("appstoreDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.bohu.pojo");
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @Bean(name="appstoreTransactionManager")
//    public DataSourceTransactionManager testTransactionManager(@Qualifier("appstoreDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name="appstoreSqlSessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("appstoreSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//}
//
