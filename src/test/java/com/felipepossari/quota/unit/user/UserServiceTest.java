package com.felipepossari.quota.unit.user;

import com.felipepossari.quota.common.DateTimeUtil;
import com.felipepossari.quota.common.exception.ErrorReason;
import com.felipepossari.quota.common.exception.ResourceNotFoundException;
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
import java.util.Optional;

import static com.felipepossari.quota.unit.base.Constants.FIRST_NAME_UPDATED;
import static com.felipepossari.quota.unit.base.Constants.LAST_NAME_UPDATED;
import static com.felipepossari.quota.unit.base.Constants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

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

    @Test
    void updateUserShouldUpdateUserSuccessfully() {
        // given
        var updatedAt = LocalDateTime.now(ZoneOffset.UTC).minusSeconds(30);
        var newUpdatedAt = LocalDateTime.now(ZoneOffset.UTC).plusSeconds(30);
        var userCreated = UserTestBuilder.anUser()
                .updatedAt(updatedAt)
                .build();
        var userToUpdate = UserTestBuilder.anUser()
                .firstName(FIRST_NAME_UPDATED)
                .lastName(LAST_NAME_UPDATED)
                .build();

        // and
        given(factory.getRepository()).willReturn(repository);
        given(repository.findById(USER_ID)).willReturn(Optional.of(userCreated));
        given(repository.update(userCreated)).willReturn(userCreated);
        given(dateTimeUtil.nowUtc()).willReturn(newUpdatedAt);
        willDoNothing().given(producer).sendUserUpdatedMessage(userCreated);

        // when
        var actualUser = service.updateUser(USER_ID, userToUpdate);

        // then
        assertEquals(FIRST_NAME_UPDATED, actualUser.getFirstName());
        assertEquals(LAST_NAME_UPDATED, actualUser.getLastName());
        assertTrue(actualUser.getUpdatedAt().isAfter(actualUser.getCreatedAt()));

        verify(factory, times(2)).getRepository();
        verify(repository).findById(USER_ID);
        verify(repository).update(userCreated);
        verify(producer).sendUserUpdatedMessage(userCreated);
    }

    @Test
    void updateUserShouldThrowResourceNotFoundExceptionWhenUserIsNotFound() {
        // given
        var userToUpdate = UserTestBuilder.anUser()
                .firstName(FIRST_NAME_UPDATED)
                .lastName(LAST_NAME_UPDATED)
                .build();

        // and
        given(factory.getRepository()).willReturn(repository);
        given(repository.findById(USER_ID)).willReturn(Optional.empty());

        // when
        var ex = assertThrows(ResourceNotFoundException.class, () -> service.updateUser(USER_ID, userToUpdate));

        // then
        assertEquals(ErrorReason.USER_NOT_FOUND, ex.getErrorReason());

        verify(factory).getRepository();
        verify(repository).findById(USER_ID);
        verify(repository, never()).update(any());
        verify(producer, never()).sendUserUpdatedMessage(any());
    }

    @Test
    void deleteUserShouldDeleteUserSuccessfully() {
        // given
        var user = UserTestBuilder.anUser().build();

        // and
        given(factory.getRepository()).willReturn(repository);
        given(repository.findById(USER_ID)).willReturn(Optional.of(user));
        willDoNothing().given(repository).delete(user);
        willDoNothing().given(producer).sendUserDeletedMessage(user);

        // when
        service.deleteUser(USER_ID);

        // then
        verify(factory, times(2)).getRepository();
        verify(repository).findById(USER_ID);
        verify(repository).delete(user);
        verify(producer).sendUserDeletedMessage(user);
    }

    @Test
    void deleteUserShouldThrowResourceNotFoundExceptionWhenUserIsNotFound() {
        // given
        var user = UserTestBuilder.anUser().build();

        // and
        given(factory.getRepository()).willReturn(repository);
        given(repository.findById(USER_ID)).willReturn(Optional.empty());

        // when
        var ex = assertThrows(ResourceNotFoundException.class, () -> service.deleteUser(USER_ID));

        // then
        assertEquals(ErrorReason.USER_NOT_FOUND, ex.getErrorReason());

        verify(factory).getRepository();
        verify(repository).findById(USER_ID);
        verify(repository, never()).delete(any());
        verify(producer, never()).sendUserDeletedMessage(any());
    }

    @Test
    void syncUserCreatedShouldCreateUserWhenUserIsNotFound() {
        // given
        var user = UserTestBuilder.anUser().build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        given(repository.findById(USER_ID)).willReturn(Optional.empty());
        given(repository.create(user)).willReturn(user);

        // when
        service.syncUserCreated(user);

        // then
        verify(factory, times(2)).getIdleRepository();
        verify(repository).findById(USER_ID);
        verify(repository).create(any());
    }

    @Test
    void syncUserCreatedShouldNotCreateUserWhenUserExists() {
        // given
        var user = UserTestBuilder.anUser().build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        given(repository.findById(USER_ID)).willReturn(Optional.of(user));

        // when
        service.syncUserCreated(user);

        // then
        verify(factory).getIdleRepository();
        verify(repository).findById(USER_ID);
        verify(repository, never()).create(any());
    }

    @Test
    void syncUserDeletedShouldDeleteUserSuccessfully() {
        // given
        var user = UserTestBuilder.anUser().build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        willDoNothing().given(repository).delete(user);

        // when
        service.syncUserDeleted(user);

        // when
        verify(factory).getIdleRepository();
        verify(repository).delete(user);
    }

    @Test
    void syncUserUpdatedShouldUpdateUserSuccessfully() {
        // given
        var userUpdated = UserTestBuilder.anUserUpdated().build();
        var userRegistered = UserTestBuilder.anUser().build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        given(repository.findById(USER_ID)).willReturn(Optional.of(userRegistered));
        given(repository.update(userUpdated)).willReturn(userUpdated);

        // when
        service.syncUserUpdated(userUpdated);

        // then
        verify(factory, times(2)).getIdleRepository();
        verify(repository).findById(USER_ID);
        verify(repository).update(userUpdated);
    }

    @Test
    void syncUserUpdatedShouldCreateUserWhenUserIsNotFound() {
        // given
        var userUpdated = UserTestBuilder.anUserUpdated().build();
        var userRegistered = UserTestBuilder.anUser().build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        given(repository.findById(USER_ID)).willReturn(Optional.empty());
        given(repository.create(userUpdated)).willReturn(userUpdated);

        // when
        service.syncUserUpdated(userUpdated);

        // then
        verify(factory, times(2)).getIdleRepository();
        verify(repository).findById(USER_ID);
        verify(repository).create(userUpdated);
        verify(repository, never()).update(any());
    }

    @Test
    void syncUserUpdatedShouldNotUpdateUserIfRegisteredUserUpdateDateTimeIsAfter() {
        // given
        var updatedAt = LocalDateTime.now(ZoneOffset.UTC);
        var userUpdated = UserTestBuilder.anUserUpdated()
                .updatedAt(updatedAt.minusSeconds(2))
                .build();
        var userRegistered = UserTestBuilder.anUser()
                .updatedAt(updatedAt)
                .build();

        // and
        given(factory.getIdleRepository()).willReturn(repository);
        given(repository.findById(USER_ID)).willReturn(Optional.of(userRegistered));

        // when
        service.syncUserUpdated(userUpdated);

        // then
        verify(factory).getIdleRepository();
        verify(repository).findById(USER_ID);
        verify(repository, never()).update(any());
        verify(repository, never()).create(any());
    }
}
