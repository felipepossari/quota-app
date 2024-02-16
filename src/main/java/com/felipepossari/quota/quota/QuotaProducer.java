package com.felipepossari.quota.quota;

import com.felipepossari.quota.common.event.Event;
import com.felipepossari.quota.common.event.EventBuilder;
import com.felipepossari.quota.common.event.EventParser;
import com.felipepossari.quota.common.event.EventType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.common.event.EventType.QUOTA_CREATED;
import static com.felipepossari.quota.common.event.EventType.QUOTA_UPDATED;

@Component
@RequiredArgsConstructor
public class QuotaProducer {

    private static final Logger logger = LoggerFactory.getLogger(QuotaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final QuotaParser quotaParser;
    private final EventParser eventParser;
    private final EventBuilder eventBuilder;

    @Value("${kafka.quota.topic}")
    private String quotaTopic;

    public void sendQuotaCreatedMessage(Quota quota) {
        logger.info("Sending quota created message. RateKey: {}", quota.getRateKey());
        kafkaTemplate.send(quotaTopic, createEvent(quota, QUOTA_CREATED));
    }

    public void sendQuotaUpdatedMessage(Quota quota) {
        logger.info("Sending quota updated message. RateKey: {}", quota.getRateKey());
        kafkaTemplate.send(quotaTopic, createEvent(quota, QUOTA_UPDATED));
    }

    private String createEvent(Quota quota, EventType eventType) {
        String data = quotaParser.parse(quota);
        Event event = eventBuilder.buildEvent(eventType, data);
        return eventParser.parse(event);
    }
}
