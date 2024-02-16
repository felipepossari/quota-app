package com.felipepossari.quota.user.api;

import com.felipepossari.quota.user.api.model.UserRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.felipepossari.quota.common.exception.ErrorReason.FIRST_NAME_EMPTY;
import static com.felipepossari.quota.common.exception.ErrorReason.LAST_NAME_EMPTY;

@Component
public class UserRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequest request = (UserRequest) target;

        if (!StringUtils.hasText(request.getFirstName())) {
            errors.reject(FIRST_NAME_EMPTY.getCode(), FIRST_NAME_EMPTY.getMessage());
        }

        if (!StringUtils.hasText(request.getLastName())) {
            errors.reject(LAST_NAME_EMPTY.getCode(), LAST_NAME_EMPTY.getMessage());
        }
    }
}
