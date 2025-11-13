package com.whhxz.blogexample.ali.oss.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "ali")
public class AliConfig {
    private String stsEndpoint;
    private String accessKeyId;
    private String accessKeySecret;
    //前端上传角色
    private String webUploadRoleArn;
}
