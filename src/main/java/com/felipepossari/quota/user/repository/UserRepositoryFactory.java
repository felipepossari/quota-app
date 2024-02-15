package com.felipepossari.quota.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class UserRepositoryFactory {

    @Qualifier("UserMySqlRepository")
    private final UserRepository userMySqlRepository;

    @Qualifier("UserEsRepository")
    private final UserRepository userEsRepository;

    @Qualifier("mysqlFromTime")
    private final LocalTime mysqlFromTime;

    @Qualifier("mysqlToTime")
    private final LocalTime mysqlToTime;

    public UserRepository getRepository(){
        LocalTime time = LocalTime.now(ZoneOffset.UTC);

        if(time.isAfter(mysqlFromTime) && time.isBefore(mysqlToTime)){
            return userMySqlRepository;
        }else{
            return userEsRepository;
        }
    }
}
