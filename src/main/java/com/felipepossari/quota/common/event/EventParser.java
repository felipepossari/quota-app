package com.felipepossari.quota.common.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipepossari.quota.common.exception.ParserErrorException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.common.exception.ErrorReason.EVENT_PARSE_FAIL;

@Component
@RequiredArgsConstructor
public class EventParser {

    private static final Logger logger = LoggerFactory.getLogger(EventParser.class);
    private final ObjectMapper objectMapper;

    public String parse(Event event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            logger.error(EVENT_PARSE_FAIL.getMessage(), e);
            throw new ParserErrorException(EVENT_PARSE_FAIL);
        }
    }
}
