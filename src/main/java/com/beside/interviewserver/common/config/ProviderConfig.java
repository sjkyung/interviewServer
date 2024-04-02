package com.beside.interviewserver.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "provider")
public class ProviderConfig {
    private String grantType = "authorization_code";
    private KakaoProperties kakao;
    private GoogleProperties google;
    private NaverProperties naver;



    @Data
    public static class KakaoProperties {
        private String clientId;
        private String grantType;
        private String redirectUri;
        private String accessTokenUri;
        private String userInfoUri;
    }
    @Data
    public static class GoogleProperties {
        private String clientId;
        private String clientSecret;
        private String grantType;
        private String redirectUri;
        private String accessTokenUri;
        private String userInfoUri;
    }

    @Data
    public static class NaverProperties {
        private String clientId;
        private String clientSecret;
        private String grantType;
        private String redirectUri;
        private String state;
        private String accessTokenUri;
        private String userInfoUri;
    }

}
