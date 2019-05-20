package com.gymclub.core.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    //slave库
    @Bean(name = "secondDataSourceProperties")
    @Qualifier("secondDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.properties.second")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "secondaryDataSource")
    @Qualifier(value = "secondaryDataSource")  //spring装配bean的唯一标识
    @ConfigurationProperties(prefix = "spring.datasource.secondary")   //application.properties配置文件中该数据源的配置前缀
    public DataSource secondaryDataSource(@Qualifier("secondDataSourceProperties") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "primaryDataSourceProperties")
    @Qualifier("primaryDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.properties.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary    //配置该数据源为主数据源
    @Bean(name = "primaryDataSource")
    @Qualifier(value = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource(@Qualifier("primaryDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

}
