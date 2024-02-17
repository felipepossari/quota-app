package com.felipepossari.quota.user.api;

import com.felipepossari.quota.common.exception.InvalidRequestException;
import com.felipepossari.quota.user.UserBuilder;
import com.felipepossari.quota.user.UserService;
import com.felipepossari.quota.user.api.model.UserRequest;
import com.felipepossari.quota.user.api.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.felipepossari.quota.user.UserConstants.API_PATH_VARIABLE_ID;
import static com.felipepossari.quota.user.UserConstants.API_PATH_VARIABLE_ID_VALUE;
import static com.felipepossari.quota.user.UserConstants.API_V1_USER_BY_ID_QUOTA_URL;
import static com.felipepossari.quota.user.UserConstants.API_V1_USER_URL;

@RestController
@RequestMapping(API_V1_USER_URL)
@RequiredArgsConstructor
public class UserController {

    private final UserBuilder builder;
    private final UserService service;
    private final UserRequestValidator validator;

    @Operation(summary = "Create user")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest body,
                                                   BindingResult bindingResult) {
        checkRequest(bindingResult);
        var user = builder.toUser(body);
        var userCreated = service.createUser(user);
        return ResponseEntity.ok(builder.toUserResponse(userCreated));
    }

    @Operation(summary = "Get user")
    @GetMapping(API_PATH_VARIABLE_ID)
    public ResponseEntity<UserResponse> getUser(@PathVariable(API_PATH_VARIABLE_ID_VALUE) String userId) {
        var user = service.getUser(userId);
        return ResponseEntity.ok(builder.toUserResponse(user));
    }

    @Operation(summary = "Update user")
    @PutMapping(API_PATH_VARIABLE_ID)
    public ResponseEntity<UserResponse> updateUser(@PathVariable(API_PATH_VARIABLE_ID_VALUE) String userId,
                                                   @Valid @RequestBody UserRequest body,
                                                   BindingResult bindingResult) {
        checkRequest(bindingResult);
        var user = builder.toUser(body);
        var userUpdated = service.updateUser(userId, user);
        return ResponseEntity.ok(builder.toUserResponse(userUpdated));
    }

    @Operation(summary = "Delete user")
    @DeleteMapping(API_PATH_VARIABLE_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable(API_PATH_VARIABLE_ID_VALUE) String userId) {
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Consume user quota")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Quota consumed successfully"),
            @ApiResponse(responseCode = "429", description = "Rate limit exceeded")
    })
    @PostMapping(API_V1_USER_BY_ID_QUOTA_URL)
    public ResponseEntity<Void> consumeQuota(@PathVariable(API_PATH_VARIABLE_ID_VALUE) String userId) {
        return ResponseEntity.ok().build();
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    private void checkRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }
    }
}
