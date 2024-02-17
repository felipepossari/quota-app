package com.felipepossari.quota.common;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

@Component
public class DateTimeUtil {

    public LocalDateTime nowUtc() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public LocalTime timeNowUtc() {
        return LocalTime.now(ZoneOffset.UTC);
    }
}
