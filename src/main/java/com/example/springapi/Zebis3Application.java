package com.example.springapi;

import com.example.springapi.common.sftp.SftpProperties;
import com.example.springapi.config.CorsProperties;
import com.example.springapi.config.JwtProperties;
import com.example.springapi.config.SecurityUserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        JwtProperties.class,
        SftpProperties.class,
        CorsProperties.class,
        SecurityUserProperties.class
})
public class Zebis3Application {

    public static void main(String[] args) {
        SpringApplication.run(Zebis3Application.class, args);
    }
}
