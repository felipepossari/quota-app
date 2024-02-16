package com.felipepossari.quota.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
public class InvalidRequestException extends RuntimeException {

    private final BindingResult bindingResult;
}
