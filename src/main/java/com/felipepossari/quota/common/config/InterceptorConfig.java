package com.felipepossari.quota.common.config;

import com.felipepossari.quota.quota.api.QuotaInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.felipepossari.quota.user.UserConstants.API_V1_USER_BY_ID_QUOTA_URL_PATTERN;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final QuotaInterceptor quotaInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(quotaInterceptor)
                .addPathPatterns(API_V1_USER_BY_ID_QUOTA_URL_PATTERN);
    }
}
