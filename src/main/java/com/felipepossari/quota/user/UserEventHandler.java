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
        log.info("Handling user event. EventType: {}", event.getEventType());

        switch (event.getEventType()) {
            case USER_CREATED -> service.syncUserCreated(userParser.parse(event.getData()));
            case USER_UPDATED -> service.syncUserUpdated(userParser.parse(event.getData()));
            case USER_DELETED -> service.syncUserDeleted(userParser.parse(event.getData()));
            default ->
                    log.info("Discarding event. Event type not ready to be handled. EventType: {}", event.getEventType());
        }
    }
}
