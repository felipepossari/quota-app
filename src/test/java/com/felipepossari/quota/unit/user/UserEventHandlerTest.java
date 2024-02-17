package com.felipepossari.quota.unit.user;

import com.felipepossari.quota.common.event.EventType;
import com.felipepossari.quota.unit.base.EventTestBuilder;
import com.felipepossari.quota.unit.base.UserTestBuilder;
import com.felipepossari.quota.user.UserEventHandler;
import com.felipepossari.quota.user.UserParser;
import com.felipepossari.quota.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.felipepossari.quota.common.event.EventType.QUOTA_UPDATED;
import static com.felipepossari.quota.common.event.EventType.USER_DELETED;
import static com.felipepossari.quota.common.event.EventType.USER_UPDATED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserEventHandlerTest {

    @Mock
    UserParser parser;

    @Mock
    UserService service;

    @InjectMocks
    UserEventHandler eventHandler;

    @Test
    void handleEventShouldSyncUserCreatedWhenEventIsUserCreated() {
        // given
        var event = EventTestBuilder.event().eventType(EventType.USER_CREATED).build();
        var user = UserTestBuilder.anUser().build();

        // and
        given(parser.parse(event.getData())).willReturn(user);
        willDoNothing().given(service).syncUserCreated(user);

        // when
        eventHandler.handleEvent(event);

        // then
        verify(service).syncUserCreated(user);
    }

    @Test
    void handleEventShouldSyncUserUpdatedWhenEventIsUserUpdated() {
        // given
        var event = EventTestBuilder.event().eventType(USER_UPDATED).build();
        var user = UserTestBuilder.anUser().build();

        // and
        given(parser.parse(event.getData())).willReturn(user);
        willDoNothing().given(service).syncUserUpdated(user);

        // when
        eventHandler.handleEvent(event);

        // then
        verify(service).syncUserUpdated(user);
    }

    @Test
    void handleEventShouldSyncUserDeletedWhenEventIsUserDeleted() {
        // given
        var event = EventTestBuilder.event().eventType(USER_DELETED).build();
        var user = UserTestBuilder.anUser().build();

        // and
        given(parser.parse(event.getData())).willReturn(user);
        willDoNothing().given(service).syncUserDeleted(user);

        // when
        eventHandler.handleEvent(event);

        // then
        verify(service).syncUserDeleted(user);
    }

    @Test
    void handleEventShouldDoNothingWhenEventIsNotReadyToBeHandled() {
        // given
        var event = EventTestBuilder.event().eventType(QUOTA_UPDATED).build();

        // when
        eventHandler.handleEvent(event);

        // then
        verify(service, never()).syncUserCreated(any());
        verify(service, never()).syncUserUpdated(any());
    }
}
