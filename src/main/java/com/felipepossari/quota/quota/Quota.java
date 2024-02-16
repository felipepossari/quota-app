package com.felipepossari.quota.quota;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Quota {
    private String rateKey;
    private Integer quotaLimit;
    private Integer remaining;
    private LocalDateTime resetTime;
}
