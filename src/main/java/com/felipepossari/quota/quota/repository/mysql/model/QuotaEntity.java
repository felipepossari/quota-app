package com.felipepossari.quota.quota.repository.mysql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "quota")
public class QuotaEntity {

    @Id
    private String rateKey;

    private Integer quotaLimit;

    private Integer remaining;

    private LocalDateTime resetTime;
}
