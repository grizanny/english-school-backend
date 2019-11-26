package com.arah.cwa.backend.controller;

import com.arah.cwa.backend.entity.AppUser;
import com.arah.cwa.backend.rest.request.SaveUserRequest;
import com.arah.cwa.backend.rest.response.UserResponse;
import com.arah.cwa.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    //TODO: убрать кастыль
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(
            @RequestParam(name = "login", required = false) List<String> loginList
    ) {
        List<UserResponse> allUsers;
        if (loginList != null) {
            allUsers = userService.findAllUsers().stream()
                    .filter(user -> loginList.contains(user.getLogin()))
                    .map(UserResponse::new)
                    .collect(Collectors.toList());
        } else {
            allUsers = userService.findAllUsers().stream()
                    .map(UserResponse::new)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid SaveUserRequest request) {
        AppUser user = userService.create(request);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestBody @Valid SaveUserRequest request) {
        AppUser user = userService.update(request);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam(name = "userId") Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
