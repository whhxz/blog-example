package com.whhxz.blogexample.ali.oss.bo;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.junit.jupiter.api.Test;

class AliOssTest {

    @Test
    public void demo() throws Exception {
        String endpoint = "sts.cn-hangzhou.aliyuncs.com";
        // 从环境变量中获取步骤1.1生成的RAM用户的访问密钥（AccessKey ID和AccessKey Secret）。
        String accessKeyId = "";
        String accessKeySecret = "";
        // 从环境变量中获取步骤1.3生成的RAM角色的RamRoleArn。
        String roleArn = "";
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
        String roleSessionName = "demo";
        // 临时访问凭证将获得角色拥有的所有权限。
        String policy = null;
        // 临时访问凭证的有效时间，单位为秒。最小值为900，最大值以当前角色设定的最大会话时间为准。当前角色最大会话时间取值范围为3600秒~43200秒，默认值为3600秒。
        // 在上传大文件或者其他较耗时的使用场景中，建议合理设置临时访问凭证的有效时间，确保在完成目标任务前无需反复调用STS服务以获取临时访问凭证。
        Long durationSeconds = 900L;
        // 发起STS请求所在的地域。建议保留默认值，默认值为空字符串（""）。
        String regionId = "";
        // 添加endpoint。适用于Java SDK 3.12.0及以上版本。
        DefaultProfile.addEndpoint(regionId, "Sts", endpoint);
        // 添加endpoint。适用于Java SDK 3.12.0以下版本。
        // DefaultProfile.addEndpoint("",regionId, "Sts", endpoint);
        // 构造default profile。
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        // 构造client。
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final AssumeRoleRequest request = new AssumeRoleRequest();
        // 适用于Java SDK 3.12.0及以上版本。
        request.setSysMethod(MethodType.POST);
        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        request.setPolicy(policy);
        request.setDurationSeconds(durationSeconds);
        final AssumeRoleResponse response = client.getAcsResponse(request);
        System.out.println("Expiration: " + response.getCredentials().getExpiration());
        System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
        System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
        System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
        System.out.println("RequestId: " + response.getRequestId());
    }

}