package com.example.springapi.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.springapi.**.infrastructure.mapper")
public class MyBatisConfig {
}
