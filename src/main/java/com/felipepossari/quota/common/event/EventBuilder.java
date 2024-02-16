package com.felipepossari.quota.common.event;

import org.springframework.stereotype.Component;

@Component
public class EventBuilder {

    public Event buildEvent(EventType eventType, String data) {
        return Event.builder()
                .data(data)
                .eventType(eventType)
                .build();
    }
}
