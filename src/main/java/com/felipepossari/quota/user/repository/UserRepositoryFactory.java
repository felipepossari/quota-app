package com.felipepossari.quota.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryFactory {

    @Qualifier("UserMySqlRepository")
    private final UserRepository userMySqlRepository;

    @Qualifier("UserEsRepository")
    private final UserRepository userEsRepository;

    public UserRepository getRepository(String repoName){
        if("UserEsRepository".equals(repoName)){
            return userEsRepository;
        }else{
            return userMySqlRepository;
        }
    }
}
