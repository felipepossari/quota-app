package com.felipepossari.quota.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ParserErrorException extends RuntimeException{

    private final ErrorReason errorReason;
}
