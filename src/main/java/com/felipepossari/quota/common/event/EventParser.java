package com.felipepossari.quota.common.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipepossari.quota.common.exception.ParserErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.common.exception.ErrorReason.EVENT_MESSAGE_PARSE_FAIL;
import static com.felipepossari.quota.common.exception.ErrorReason.EVENT_PARSE_FAIL;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventParser {

    private final ObjectMapper objectMapper;

    public String parse(Event event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error(EVENT_PARSE_FAIL.getMessage(), e);
            throw new ParserErrorException(EVENT_PARSE_FAIL);
        }
    }

    public Event parseMessage(String message) {
        try {
            return objectMapper.readValue(message, Event.class);
        } catch (JsonProcessingException e) {
            log.error(EVENT_MESSAGE_PARSE_FAIL.getMessage(), e);
            throw new ParserErrorException(EVENT_MESSAGE_PARSE_FAIL);
        }
    }
}
