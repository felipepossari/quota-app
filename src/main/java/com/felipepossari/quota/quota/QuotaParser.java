package com.felipepossari.quota.quota;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipepossari.quota.common.exception.ParserErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.common.exception.ErrorReason.USER_PARSE_FAIL;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuotaParser {

    private final ObjectMapper objectMapper;

    public String parse(Quota quota) {
        try {
            return objectMapper.writeValueAsString(quota);
        } catch (JsonProcessingException e) {
            log.error(USER_PARSE_FAIL.getMessage(), e);
            throw new ParserErrorException(USER_PARSE_FAIL);
        }
    }
}
