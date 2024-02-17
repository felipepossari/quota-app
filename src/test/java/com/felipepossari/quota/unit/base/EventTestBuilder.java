package com.felipepossari.quota.unit.base;

import com.felipepossari.quota.common.event.Event;
import com.felipepossari.quota.common.event.EventType;

import java.time.OffsetDateTime;

import static com.felipepossari.quota.unit.base.Constants.EVENT_ID;
import static com.felipepossari.quota.unit.base.Constants.EVENT_TIME;

public class EventTestBuilder {

    private OffsetDateTime eventTime = EVENT_TIME;
    private String eventId = EVENT_ID;
    private EventType eventType;
    private String data = "";

    private EventTestBuilder() {
    }

    public static EventTestBuilder event() {
        return new EventTestBuilder();
    }

    public EventTestBuilder eventTime(OffsetDateTime eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public EventTestBuilder eventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public EventTestBuilder eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public EventTestBuilder data(String data) {
        this.data = data;
        return this;
    }

    public Event build() {
        return Event.builder()
                .eventType(eventType)
                .eventId(eventId)
                .data(data)
                .eventTime(eventTime)
                .build();
    }
}
