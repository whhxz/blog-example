package com.whhxz.blogexample.ali.oss.bo;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.whhxz.blogexample.ali.oss.config.AliConfig;
import com.whhxz.blogexample.ali.oss.controller.vo.AliStsTokenVo;

public class AliOss {
    private AliConfig config;

    public AliOss(AliConfig config) {
        this.config = config;
    }

    public AliStsTokenVo stkToken() throws ClientException {
        String endpoint = config.getStsEndpoint();
        String accessKeyId = config.getAccessKeyId();
        String accessKeySecret = config.getAccessKeySecret();
        String roleArn = config.getWebUploadRoleArn();
        String roleSessionName = "example";
        // 临时访问凭证将获得角色拥有的所有权限。
        String policy = null;
        // 临时访问凭证的有效时间，单位为秒。最小值为900，最大值以当前角色设定的最大会话时间为准。当前角色最大会话时间取值范围为3600秒~43200秒，默认值为3600秒。
        // 在上传大文件或者其他较耗时的使用场景中，建议合理设置临时访问凭证的有效时间，确保在完成目标任务前无需反复调用STS服务以获取临时访问凭证。
        Long durationSeconds = 900L;
        // 发起STS请求所在的地域。建议保留默认值，默认值为空字符串（""）。
        String regionId = "";
        // 添加endpoint。适用于Java SDK 3.12.0及以上版本。
        DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
        // 构造default profile。
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        // 构造client。
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setSysMethod(MethodType.POST);
        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        request.setPolicy(policy);
        request.setDurationSeconds(durationSeconds);
        final AssumeRoleResponse response = client.getAcsResponse(request);
        return new AliStsTokenVo(response.getCredentials());
    }
}
