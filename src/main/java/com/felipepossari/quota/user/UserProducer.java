package com.felipepossari.quota.user;

import com.felipepossari.quota.common.event.Event;
import com.felipepossari.quota.common.event.EventBuilder;
import com.felipepossari.quota.common.event.EventParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.common.event.EventType.USER_CREATED;

@Component
@RequiredArgsConstructor
public class UserProducer {

    private static final Logger logger = LoggerFactory.getLogger(UserProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserParser userParser;
    private final EventParser eventParser;
    private final EventBuilder eventBuilder;

    @Value("${kafka.user.topic}")
    private String userTopic;

    public void sendUserCreatedMessage(User user) {
        logger.info("Sending user created message. UserId: {}", user.getId());
        String data = userParser.parse(user);
        Event event = eventBuilder.buildEvent(USER_CREATED, data);
        kafkaTemplate.send(userTopic, eventParser.parse(event));
    }
}
