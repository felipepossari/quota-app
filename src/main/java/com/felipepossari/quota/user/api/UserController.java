package com.felipepossari.quota.user.api;

import com.felipepossari.quota.user.UserBuilder;
import com.felipepossari.quota.user.UserService;
import com.felipepossari.quota.user.api.model.UserRequest;
import com.felipepossari.quota.user.api.model.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserBuilder builder;
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest body) {
        var user = builder.toUser(body);
        var userCreated = service.createUser(user);
        return ResponseEntity.ok(builder.toUserResponse(userCreated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String userId) {
        var user = service.getUser(userId);
        return ResponseEntity.ok(builder.toUserResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") String userId,
                                                   @Valid @RequestBody UserRequest body) {
        var user = builder.toUser(body);
        var userUpdated = service.updateUser(userId, user);
        return ResponseEntity.ok(builder.toUserResponse(userUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String userId){
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/quota")
    public ResponseEntity<Void> consumeQuota(@PathVariable("id") String userId){
        return ResponseEntity.ok().build();
    }
}
