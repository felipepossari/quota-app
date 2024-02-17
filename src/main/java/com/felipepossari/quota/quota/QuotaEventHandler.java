package com.felipepossari.quota.quota;

import com.felipepossari.quota.common.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuotaEventHandler {

    private final QuotaParser quotaParser;
    private final QuotaService service;

    public void handleEvent(Event event) {
        Quota quota = quotaParser.parse(event.getData());
        log.info("Handling quota message. EventType: {} RateKey: {}", event.getEventType(), quota.getRateKey());

        switch (event.getEventType()) {
            case QUOTA_CREATED -> service.syncQuotaCreated(quota);
            case QUOTA_UPDATED -> service.syncQuotaUpdated(quota);
            default ->
                    log.info("Discarding event. Event type not ready to be handled. EventType: {}", event.getEventType());
        }
    }
}
