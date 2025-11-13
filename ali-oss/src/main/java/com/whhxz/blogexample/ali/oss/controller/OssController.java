package com.whhxz.blogexample.ali.oss.controller;

import com.aliyuncs.exceptions.ClientException;
import com.whhxz.blogexample.ali.oss.bo.AliOss;
import com.whhxz.blogexample.ali.oss.config.AliConfig;
import com.whhxz.blogexample.ali.oss.controller.vo.AliStsTokenVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oss")
@RequiredArgsConstructor
public class OssController {
    private final AliConfig aliConfig;

    @GetMapping("/stsToken")
    public AliStsTokenVo stsToken(HttpServletRequest request) throws ClientException {
        String sessionKey = "ALI_OSS_STS_TOKEN";
        AliStsTokenVo aliOssStsToken = (AliStsTokenVo) request.getSession().getAttribute(sessionKey);
        if (aliOssStsToken == null || aliOssStsToken.isExpired()) {
            aliOssStsToken = new AliOss(aliConfig).stkToken();
        }
        request.getSession().setAttribute(sessionKey, aliOssStsToken);
        return aliOssStsToken;
    }
}
