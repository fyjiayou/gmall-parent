package com.fystart.gmall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fy
 * @date 2022/12/2 22:54
 */
@SpringBootApplication
@ComponentScan("com.fystart.gmall")
@EnableDiscoveryClient
public class ProductMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductMainApplication.class, args);
    }
}
