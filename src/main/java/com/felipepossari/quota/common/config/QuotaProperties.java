package com.felipepossari.quota.common.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "quota")
@NoArgsConstructor
@Getter
@Setter
public class QuotaProperties {

    private Integer limit;

}
