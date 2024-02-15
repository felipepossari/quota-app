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

    public UserRepository getRepository(){
        var dayFrom = LocalTime.of(9, 00);
        var dayTo = LocalTime.of(17, 00);
        LocalTime time = LocalTime.now(ZoneOffset.UTC);

        if(time.isAfter(dayFrom) && time.isBefore(dayTo)){
            return userMySqlRepository;
        }else{
            return userEsRepository;
        }
    }
}
