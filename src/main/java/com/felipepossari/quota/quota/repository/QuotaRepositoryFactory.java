package com.felipepossari.quota.quota.repository;

import com.felipepossari.quota.common.DateTimeUtil;
import com.felipepossari.quota.common.config.DataSourcePeriodProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class QuotaRepositoryFactory {

    @Qualifier("QuotaMysqlRepository")
    private final QuotaRepository quotaMySqlRepository;

    @Qualifier("QuotaEsRepository")
    private final QuotaRepository quotaEsRepository;

    private final DataSourcePeriodProperties properties;
    private final DateTimeUtil dateTimeUtil;

    public QuotaRepository getRepository() {
        LocalTime time = dateTimeUtil.timeNowUtc();

        if (time.compareTo(properties.getFromTime()) >= 0 && time.compareTo(properties.getToTime()) <=0) {
            return quotaMySqlRepository;
        } else {
            return quotaEsRepository;
        }
    }

    public QuotaRepository getIdleRepository() {
        LocalTime time = dateTimeUtil.timeNowUtc();

        if (time.compareTo(properties.getFromTime()) >= 0 && time.compareTo(properties.getToTime()) <=0) {
            return quotaEsRepository;
        } else {
            return quotaMySqlRepository;
        }
    }
}
