package com.whhxz.blogexample.ali.oss.controller.vo;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AliStsTokenVo {
    @JsonIgnore
    private Long expired;
    // 临时授权
    private String accessKeyId;
    private String accessKeySecret;
    //安全令牌
    private String securityToken;

    public AliStsTokenVo(AssumeRoleResponse.Credentials credentials) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(credentials.getExpiration(), DateTimeFormatter.ISO_DATE_TIME);
        this.expired = zonedDateTime.toInstant().toEpochMilli() - 3 * 60 * 1000L;
        this.accessKeyId = credentials.getAccessKeyId();
        this.accessKeySecret = credentials.getAccessKeySecret();
        this.securityToken = credentials.getSecurityToken();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expired;
    }
}
