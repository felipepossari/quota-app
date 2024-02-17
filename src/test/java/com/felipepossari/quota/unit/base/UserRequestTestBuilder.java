package com.felipepossari.quota.unit.base;

import com.felipepossari.quota.user.api.model.UserRequest;

import static com.felipepossari.quota.unit.base.Constants.FIRST_NAME;
import static com.felipepossari.quota.unit.base.Constants.LAST_NAME;

public class UserRequestTestBuilder {

    private String firstName = FIRST_NAME;
    private String lastName = LAST_NAME;

    private UserRequestTestBuilder() {
    }

    public static UserRequestTestBuilder aRequest() {
        return new UserRequestTestBuilder();
    }

    public UserRequestTestBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserRequestTestBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserRequest build() {
        return new UserRequest(firstName, lastName);
    }
}
