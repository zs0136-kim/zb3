package com.example.springapi.common.sftp;

import com.jcraft.jsch.JSch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SFTP 用の共通コンフィグ。
 */
@Configuration
public class SftpConfig {

    @Bean
    public JSch jsch() {
        return new JSch();
    }
}

