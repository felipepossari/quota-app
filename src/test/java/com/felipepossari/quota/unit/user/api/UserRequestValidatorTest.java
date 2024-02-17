package com.felipepossari.quota.unit.user.api;

import com.felipepossari.quota.unit.base.UserRequestTestBuilder;
import com.felipepossari.quota.user.api.UserRequestValidator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import static com.felipepossari.quota.common.exception.ErrorReason.FIRST_NAME_EMPTY;
import static com.felipepossari.quota.common.exception.ErrorReason.LAST_NAME_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRequestValidatorTest {

    private UserRequestValidator validator = new UserRequestValidator();

    @Test
    void validateShouldReturnNoErrorsWhenRequestIsValid() {
        // given
        var request = UserRequestTestBuilder.aRequest().build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertFalse(errors.hasErrors());
    }

    @Test
    void validateShouldReturnErrorWhenFirstNameIsEmpty() {
        // given
        var request = UserRequestTestBuilder.aRequest().firstName("").build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertTrue(errors.hasErrors());
        assertEquals(FIRST_NAME_EMPTY.getCode(), errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateShouldReturnErrorWhenLastNameIsEmpty() {
        // given
        var request = UserRequestTestBuilder.aRequest().lastName("").build();
        var errors = new BeanPropertyBindingResult(request, "");

        // when
        validator.validate(request, errors);

        // then
        assertTrue(errors.hasErrors());
        assertEquals(LAST_NAME_EMPTY.getCode(), errors.getAllErrors().get(0).getCode());
    }
}
