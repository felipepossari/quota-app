package com.felipepossari.quota.quota;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipepossari.quota.common.exception.ParserErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.common.exception.ErrorReason.QUOTA_DATA_PARSE_FAIL;
import static com.felipepossari.quota.common.exception.ErrorReason.QUOTA_PARSE_FAIL;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuotaParser {

    private final ObjectMapper objectMapper;

    public String parse(Quota quota) {
        try {
            return objectMapper.writeValueAsString(quota);
        } catch (JsonProcessingException e) {
            log.error(QUOTA_PARSE_FAIL.getMessage(), e);
            throw new ParserErrorException(QUOTA_PARSE_FAIL);
        }
    }

    public Quota parse(String data) {
        try {
            return objectMapper.readValue(data, Quota.class);
        } catch (JsonProcessingException e) {
            log.error(QUOTA_DATA_PARSE_FAIL.getMessage(), e);
            throw new ParserErrorException(QUOTA_DATA_PARSE_FAIL);
        }
    }
}
