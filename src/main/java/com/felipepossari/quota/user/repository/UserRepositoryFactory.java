package com.felipepossari.quota.user.repository;

import com.felipepossari.quota.common.DateTimeUtil;
import com.felipepossari.quota.common.config.DataSourcePeriodProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class UserRepositoryFactory {

    @Qualifier("UserMySqlRepository")
    private final UserRepository userMySqlRepository;

    @Qualifier("UserEsRepository")
    private final UserRepository userEsRepository;

    private final DataSourcePeriodProperties properties;
    private final DateTimeUtil dateTimeUtil;

    public UserRepository getRepository() {
        LocalTime time = dateTimeUtil.timeNowUtc();

        if (time.compareTo(properties.getFromTime()) >= 0 && time.compareTo(properties.getToTime()) <=0) {
            return userMySqlRepository;
        } else {
            return userEsRepository;
        }
    }

    public UserRepository getIdleRepository() {
        LocalTime time = dateTimeUtil.timeNowUtc();

        if (time.compareTo(properties.getFromTime()) >= 0 && time.compareTo(properties.getToTime()) <=0) {
            return userEsRepository;
        } else {
            return userMySqlRepository;
        }
    }
}
