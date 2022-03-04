//package com.bohu.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
//import org.apache.ibatis.session.SqlSessionFactory;
////import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
////import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
////import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
////import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
////import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * @ClassName AppstoreDataSourceConfig
// * @Author shenbohu
// * @Date 2021/12/292:33 PM
// * @Version 1.0
// **/
//@Configuration
//@MapperScan(basePackages = "com.bohu.dao.Appstore",sqlSessionTemplateRef = "appstoreSqlSessionTemplate")
//public class AppstoreDataSourceConfig {
//
//    @Bean(name="appstoreDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.appstore")
//    public DataSource appstoreDataSource()  {
//        return DataSourceBuilder.create().build() ;
//    }
//
//
//
//
//    @Bean(name="appstoreSqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory (@Qualifier("appstoreDataSource") DataSource dataSource) throws  Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(new DataSourceProxy(dataSource));
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/Appstore/*.xml"));
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.bohu.pojo");
//        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
//        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
//        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
//        return sqlSessionFactory;
//
//    }
//
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
//
//}
