package com.felipepossari.quota.user;

import com.felipepossari.quota.common.event.Event;
import com.felipepossari.quota.common.event.EventParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {

    private final EventParser eventParser;
    private final UserEventHandler userEventHandler;

    @KafkaListener(topics = "user", groupId = "user")
    public void consumeUserMessage(String message) {
        try {
            Event event = eventParser.parseMessage(message);
            userEventHandler.handleEvent(event);
        } catch (Exception e) {
            log.error("Failure to consume user message. Discarding message", e);
        }
    }
}
