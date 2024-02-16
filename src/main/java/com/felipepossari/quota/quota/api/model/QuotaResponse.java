package com.felipepossari.quota.quota.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QuotaResponse {
    private String rateKey;
    private Integer quotaLimit;
    private Integer remaining;
    private LocalDateTime resetTime;
    private QuotaResponseStatus status;
}
