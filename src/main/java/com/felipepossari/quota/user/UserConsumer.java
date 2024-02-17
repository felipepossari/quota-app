package com.felipepossari.quota.user;

import com.felipepossari.quota.common.event.Event;
import com.felipepossari.quota.common.event.EventParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.user.UserConstants.USER_TOPIC_GROUP_ID;
import static com.felipepossari.quota.user.UserConstants.USER_TOPIC_NAME;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {

    private final EventParser eventParser;
    private final UserEventHandler userEventHandler;

    @KafkaListener(topics = USER_TOPIC_NAME, groupId = USER_TOPIC_GROUP_ID)
    public void consumeUserMessage(String message) {
        try {
            Event event = eventParser.parseMessage(message);
            userEventHandler.handleEvent(event);
        } catch (Exception e) {
            log.error("Failure to consume user message. Discarding message", e);
        }
    }
}
