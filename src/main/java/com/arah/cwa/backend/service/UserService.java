package com.arah.cwa.backend.service;

import com.arah.cwa.backend.entity.AppUser;
import com.arah.cwa.backend.rest.request.SaveUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<AppUser> findAllUsers();

    Optional<AppUser> findUserByLogin(String login);

    AppUser create(SaveUserRequest userRequest);

    AppUser update(SaveUserRequest userRequest);

    void deleteById(Integer userId);
}
