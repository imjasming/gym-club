package com.gymclub.sso.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Xiaoming.
 * Created on 2019/05/25 16:05.
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.gymclub.sso.mapper"})
public class MybatisConfig {
}
