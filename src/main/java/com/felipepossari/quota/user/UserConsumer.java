package com.felipepossari.quota.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {

    @KafkaListener(topics = "user", groupId = "user")
    public void consumeUserMessage(String message) {
        log.info("User message received. Message: {}", message);
    }
}
