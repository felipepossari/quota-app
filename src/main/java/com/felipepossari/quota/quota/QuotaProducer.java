package com.felipepossari.quota.quota;

import com.felipepossari.quota.common.event.Event;
import com.felipepossari.quota.common.event.EventBuilder;
import com.felipepossari.quota.common.event.EventParser;
import com.felipepossari.quota.common.event.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.common.event.EventType.QUOTA_CREATED;
import static com.felipepossari.quota.common.event.EventType.QUOTA_UPDATED;
import static com.felipepossari.quota.quota.QuotaConstants.QUOTA_TOPIC_NAME;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuotaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final QuotaParser quotaParser;
    private final EventParser eventParser;
    private final EventBuilder eventBuilder;

    public void sendQuotaCreatedMessage(Quota quota) {
        log.info("Sending quota created message. RateKey: {}", quota.getRateKey());
        kafkaTemplate.send(QUOTA_TOPIC_NAME, createEvent(quota, QUOTA_CREATED));
    }

    public void sendQuotaUpdatedMessage(Quota quota) {
        log.info("Sending quota updated message. RateKey: {}", quota.getRateKey());
        kafkaTemplate.send(QUOTA_TOPIC_NAME, createEvent(quota, QUOTA_UPDATED));
    }

    private String createEvent(Quota quota, EventType eventType) {
        String data = quotaParser.parse(quota);
        Event event = eventBuilder.buildEvent(eventType, data);
        return eventParser.parse(event);
    }
}
