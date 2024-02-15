package com.felipepossari.quota.common.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final ErrorReason errorReason;

    public ResourceNotFoundException(ErrorReason errorReason){
        this.errorReason = errorReason;
    }
}
