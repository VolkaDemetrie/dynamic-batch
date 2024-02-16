package com.volka.dynamicbatch;

import com.volka.dynamicbatch.core.properties.MybatisProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({MybatisProperties.class})
@SpringBootApplication
public class DynamicBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicBatchApplication.class, args);
    }

}
