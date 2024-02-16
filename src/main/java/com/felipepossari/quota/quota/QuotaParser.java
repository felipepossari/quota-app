package com.felipepossari.quota.quota;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipepossari.quota.common.exception.ParserErrorException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.common.exception.ErrorReason.USER_PARSE_FAIL;

@Component
@RequiredArgsConstructor
public class QuotaParser {

    private static final Logger logger = LoggerFactory.getLogger(QuotaParser.class);

    private final ObjectMapper objectMapper;

    public String parse(Quota quota) {
        try {
            return objectMapper.writeValueAsString(quota);
        } catch (JsonProcessingException e) {
            logger.error(USER_PARSE_FAIL.getMessage(), e);
            throw new ParserErrorException(USER_PARSE_FAIL);
        }
    }
}
