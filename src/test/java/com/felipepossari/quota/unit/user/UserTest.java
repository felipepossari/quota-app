package com.felipepossari.quota.unit.user;

import com.felipepossari.quota.unit.base.UserTestBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.felipepossari.quota.unit.base.Constants.FIRST_NAME_UPDATED;
import static com.felipepossari.quota.unit.base.Constants.LAST_NAME_UPDATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void updateNameShouldUpdateNameAndUpdatedAtSuccessfully() {
        // given
        var user = UserTestBuilder.anUser().build();
        var updatedAt = LocalDateTime.now(ZoneOffset.UTC);

        // when
        user.updateName(FIRST_NAME_UPDATED, LAST_NAME_UPDATED, updatedAt);

        // then
        assertEquals(FIRST_NAME_UPDATED, user.getFirstName());
        assertEquals(LAST_NAME_UPDATED, user.getLastName());
        assertTrue(user.getUpdatedAt().isEqual(updatedAt));
    }
}
