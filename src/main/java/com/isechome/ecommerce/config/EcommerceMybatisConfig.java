package com.isechome.ecommerce.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * isechome数据库对应的配置
 */
@MapperScan(basePackages = "com.isechome.ecommerce.mapper.ecommerce,com.isechome.ecommerce.security.mapper.ecommerce", sqlSessionFactoryRef = "ecommerceMybatisSqlSessionFactory")
//指定Mapper接口的地址
@Configuration
public class EcommerceMybatisConfig {

    //创建数据源
    @Bean("ecommerceMybatisDataSource")
    //以spring.datasource.ecommerce开头的属性都会set到new DruidDataSource()中
    @ConfigurationProperties(prefix = "spring.datasource.ecommerce")
    @Primary
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * @Description 注册事务管理器 注意在service中需要绑定事务管理器的id
     * @Author zhaofy
     * @Date  2021/3/22
     * @Param []
     * @return org.springframework.transaction.PlatformTransactionManager
     **/
    @Bean("ecommerceMybatisPlatformTransactionManager")
    @Primary
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    //配置SqlSession工厂
    @Bean("ecommerceMybatisSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //指定数据源
        factoryBean.setDataSource(dataSource());
        //指定所有mapper.xml所在路径
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:mapping/ecommerce/*.xml"));
        return factoryBean.getObject();
    }

}