package com.felipepossari.quota.user;

import com.felipepossari.quota.common.event.Event;
import com.felipepossari.quota.common.event.EventBuilder;
import com.felipepossari.quota.common.event.EventParser;
import com.felipepossari.quota.common.event.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.common.event.EventType.USER_CREATED;
import static com.felipepossari.quota.common.event.EventType.USER_DELETED;
import static com.felipepossari.quota.common.event.EventType.USER_UPDATED;
import static com.felipepossari.quota.user.UserConstants.USER_TOPIC_NAME;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserParser userParser;
    private final EventParser eventParser;
    private final EventBuilder eventBuilder;

    public void sendUserCreatedMessage(User user) {
        log.info("Sending user created message. UserId: {}", user.getId());
        kafkaTemplate.send(USER_TOPIC_NAME, createEvent(user, USER_CREATED));
    }

    public void sendUserUpdatedMessage(User user) {
        log.info("Sending user updated message. UserId: {}", user.getId());
        kafkaTemplate.send(USER_TOPIC_NAME, createEvent(user, USER_UPDATED));
    }

    public void sendUserDeletedMessage(User user) {
        log.info("Sending user deleted message. UserId: {}", user.getId());
        kafkaTemplate.send(USER_TOPIC_NAME, createEvent(user, USER_DELETED));
    }

    private String createEvent(User user, EventType eventType) {
        String data = userParser.parse(user);
        Event event = eventBuilder.buildEvent(eventType, data);
        return eventParser.parse(event);
    }
}
