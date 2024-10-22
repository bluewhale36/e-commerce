package com.guncat.ecommerce.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * File 관련 Bean 객체 등록의 Config.
 */
@Configuration
public class FileConfig {

    /**
     * 파일 저장 경로 반환.
     * @return {@code String} 파일 저장 경로.
     */
    @Bean(name="filePath")
    public String getFilePath() {
        return "/Users/wooseunghun/src/ECommerceResources";
    }
}
