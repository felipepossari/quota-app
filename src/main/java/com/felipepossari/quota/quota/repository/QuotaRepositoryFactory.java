package com.felipepossari.quota.quota.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class QuotaRepositoryFactory {

    @Qualifier("QuotaMysqlRepository")
    private final QuotaRepository quotaMySqlRepository;

    @Qualifier("QuotaEsRepository")
    private final QuotaRepository quotaEsRepository;

    @Qualifier("mysqlFromTime")
    private final LocalTime mysqlFromTime;

    @Qualifier("mysqlToTime")
    private final LocalTime mysqlToTime;

    public QuotaRepository getRepository() {
        LocalTime time = LocalTime.now(ZoneOffset.UTC);

        if (time.isAfter(mysqlFromTime) && time.isBefore(mysqlToTime)) {
            return quotaMySqlRepository;
        } else {
            return quotaEsRepository;
        }
    }
}
