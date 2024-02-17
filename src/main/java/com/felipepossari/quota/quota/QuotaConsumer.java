package com.felipepossari.quota.quota;

import com.felipepossari.quota.common.event.Event;
import com.felipepossari.quota.common.event.EventParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.quota.QuotaConstants.TOPIC_GROUP_ID;
import static com.felipepossari.quota.quota.QuotaConstants.TOPIC_NAME;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuotaConsumer {

    private final EventParser eventParser;
    private final QuotaEventHandler quotaEventHandler;

    @KafkaListener(topics = TOPIC_NAME, groupId = TOPIC_GROUP_ID)
    public void consumeQuotaMessage(String message) {
        try {
            Event event = eventParser.parseMessage(message);
            quotaEventHandler.handleEvent(event);
        } catch (Exception e) {
            log.error("Failure to consume message. Discarding message", e);
        }
    }
}
