package com.felipepossari.quota.user;

import com.felipepossari.quota.common.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventHandler {

    private final UserParser userParser;
    private final UserService service;

    public void handleEvent(Event event) {
        User user = userParser.parse(event.getData());
        log.info("Handling user message. EventType: {} UserId: {}", event.getEventType(), user.getId());

        switch (event.getEventType()) {
            case USER_CREATED -> service.syncUserCreated(user);
            case USER_UPDATED -> service.syncUserUpdated(user);
            case USER_DELETED -> service.syncUserDeleted(user);
            default ->
                    log.info("Discarding event. Event type not ready to be handled. EventType: {}", event.getEventType());
        }
    }
}
