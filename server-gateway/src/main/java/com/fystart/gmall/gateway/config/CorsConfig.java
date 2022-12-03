package com.fystart.gmall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 全局跨域配置信息
 * @author fy
 * @date 2022/12/3 14:43
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {

        //创建对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许跨域的网络地址
        corsConfiguration.addAllowedOrigin("*");
        //允许cookie携带
        corsConfiguration.setAllowCredentials(true);
        //允许的请求方式
        corsConfiguration.addAllowedMethod("*");
        //允许携带头信息
        corsConfiguration.addAllowedHeader("*");

        //创建配置对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();

        //设置配置
        configurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsWebFilter(configurationSource);
    }
}
