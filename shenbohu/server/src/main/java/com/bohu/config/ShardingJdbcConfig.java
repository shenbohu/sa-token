package com.bohu.config;

import com.github.pagehelper.PageHelper;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
//import org.apache.shardingsphere.core.metadata.datasource.ShardingDataSourceMetaData;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
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
import java.sql.SQLException;
import java.util.*;

/**
 * @ClassName ShardingJdbcConfig
 * @Author shenbohu
 * @Date 2022/1/210:25 PM
 * @Version 1.0
 **/

//分库分表
@Configuration
@MapperScan(basePackages = "com.bohu.dao.AppstoreSharding",sqlSessionTemplateRef = "ShardingSqlSessionTemplate")
public class ShardingJdbcConfig {

    @Bean(name="m1")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.m1")
    public DataSource m1() {

        return DataSourceBuilder.create().build();
    }

    @Bean(name="m2")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.m2")
    public DataSource m2() {
        return DataSourceBuilder.create().build();
    }


    //创建数据源，需要把分库的库都传递进去
    @Bean("shardingdataSource")
    public DataSource dataSource(@Qualifier("m1") DataSource m1,
                                 @Qualifier("m2") DataSource m2) throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
        dataSourceMap.put("m1", m1);
        dataSourceMap.put("m2", m2);
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setDefaultDataSourceName("m1");
        //如果有多个数据表需要分表，依次添加到这里
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        Properties p = new Properties();
        p.setProperty("sql.show", Boolean.TRUE.toString());
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig,p);
        return dataSource;
    }

    // 定义t_order表的分片策略
    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("course","m$->{1..2}.course_${1..2}");
        result.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("cid", "course_$->{cid % 2 + 1}"));
        result.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "m$->{user_id % 2 + 1 }"));
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());

        return result;
    }


    // 定义主键生成策略
    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE","cid");
        return result;
    }




    @Bean(name="ShardingSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory (@Qualifier("shardingdataSource") DataSource dataSource) throws  Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(new DataSourceProxy(dataSource));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/AppstoreSharding/*.xml"));
       // sqlSessionFactoryBean.setTypeAliasesPackage("com.bohu.pojo");
//        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
//        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
//        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);

        return sqlSessionFactoryBean.getObject();

    }


    @Bean(name="ShardingTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("shardingdataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean(name="ShardingSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("ShardingSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    // 创建事务管理器
    @Bean("shardingTransactionManger")
    public DataSourceTransactionManager shardingTransactionManger(@Qualifier("shardingdataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }



    //分页
    @Bean(name="pageHelper")
    public PageHelper getPageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "true");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
