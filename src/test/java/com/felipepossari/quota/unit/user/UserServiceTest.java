package com.felipepossari.quota.unit.user;

import com.felipepossari.quota.common.DateTimeUtil;
import com.felipepossari.quota.unit.base.UserTestBuilder;
import com.felipepossari.quota.user.User;
import com.felipepossari.quota.user.UserProducer;
import com.felipepossari.quota.user.UserService;
import com.felipepossari.quota.user.repository.UserRepository;
import com.felipepossari.quota.user.repository.UserRepositoryFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepositoryFactory factory;

    @Mock
    UserRepository repository;

    @Mock
    DateTimeUtil dateTimeUtil;

    @Mock
    UserProducer producer;

    @InjectMocks
    UserService service;

    @Test
    void createUserShouldCreateUserSuccessfully() {
        // given
        User user = UserTestBuilder.anUser()
                .id(null)
                .createdAt(null)
                .updatedAt(null)
                .build();

        // and
        given(factory.getRepository()).willReturn(repository);
        given(repository.create(user)).willReturn(user);
        given(dateTimeUtil.nowUtc()).willReturn(LocalDateTime.now(ZoneOffset.UTC));
        willDoNothing().given(producer).sendUserCreatedMessage(user);

        // when
        var actualUser = service.createUser(user);

        // then
        assertNotNull(actualUser.getId());
        assertNotNull(actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());

        verify(factory).getRepository();
        verify(repository).create(user);
        verify(producer).sendUserCreatedMessage(user);
    }
}
