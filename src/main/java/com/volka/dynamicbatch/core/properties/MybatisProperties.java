package com.volka.dynamicbatch.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "mybatis")
public class MybatisProperties {
    private String mapperLocations;
    private String configLocation;
}
