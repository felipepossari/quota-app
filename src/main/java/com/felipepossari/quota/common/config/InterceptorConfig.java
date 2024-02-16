package com.felipepossari.quota.common.config;

import com.felipepossari.quota.quota.api.QuotaInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final QuotaInterceptor quotaInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(quotaInterceptor)
                .addPathPatterns("/v1/users/*/quota");
    }
}
