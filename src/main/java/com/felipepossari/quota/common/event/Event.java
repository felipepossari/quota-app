package com.felipepossari.quota.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Event {
    @Builder.Default
    private OffsetDateTime eventTime = OffsetDateTime.now(ZoneOffset.UTC);
    @Builder.Default
    private String eventId = UUID.randomUUID().toString();
    private EventType eventType;
    private String data;
}
