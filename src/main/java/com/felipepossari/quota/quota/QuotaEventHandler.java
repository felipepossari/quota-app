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
        log.info("Handling quota event. EventType: {}", event.getEventType());

        switch (event.getEventType()) {
            case QUOTA_CREATED -> service.syncQuotaCreated(quotaParser.parse(event.getData()));
            case QUOTA_UPDATED -> service.syncQuotaUpdated(quotaParser.parse(event.getData()));
            default ->
                    log.info("Discarding event. Event type not ready to be handled. EventType: {}", event.getEventType());
        }
    }
}
