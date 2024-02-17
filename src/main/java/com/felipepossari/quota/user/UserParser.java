package com.felipepossari.quota.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipepossari.quota.common.exception.ParserErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.felipepossari.quota.common.exception.ErrorReason.USER_DATA_PARSE_FAIL;
import static com.felipepossari.quota.common.exception.ErrorReason.USER_PARSE_FAIL;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserParser {

    private final ObjectMapper objectMapper;

    public String parse(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            log.error(USER_PARSE_FAIL.getMessage(), e);
            throw new ParserErrorException(USER_PARSE_FAIL);
        }
    }

    public User parse(String data) {
        try {
            return objectMapper.readValue(data, User.class);
        } catch (JsonProcessingException e) {
            log.error(USER_DATA_PARSE_FAIL.getMessage(), e);
            throw new ParserErrorException(USER_DATA_PARSE_FAIL);
        }
    }
}
