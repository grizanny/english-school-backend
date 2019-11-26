package com.arah.cwa.backend.service.impl;

import com.arah.cwa.backend.entity.AppUser;
import com.arah.cwa.backend.entity.UserRole;
import com.arah.cwa.backend.exception.UserAlreadyExistsException;
import com.arah.cwa.backend.exception.UserNotFound;
import com.arah.cwa.backend.repository.UserRepository;
import com.arah.cwa.backend.rest.request.SaveUserRequest;
import com.arah.cwa.backend.security.utils.RoleUtils;
import com.arah.cwa.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<AppUser> findUserByLogin(String login) {
        return userRepository.findAppUserByLogin(login);
    }

    private static void preChangeAuthorizationCheck(UserRole userRole) {
        switch (userRole) {
            case ROLE_MANAGER:
            case ROLE_ADMIN:
                // Create manager and admin user can only manager
                RoleUtils.preSaveCheck(UserRole.ROLE_MANAGER);
                break;
            case ROLE_STUDENT:
            case ROLE_TEACHER:
                // Create student and teacher can only admin
                RoleUtils.preSaveCheck(UserRole.ROLE_ADMIN);
                break;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = findUserByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Username = %s not found", username)));
        return new User(user.getLogin(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
    }

    @Transactional
    @Override
    public AppUser create(SaveUserRequest userRequest) {
        preChangeAuthorizationCheck(userRequest.getRole());

        AppUser user = new AppUser(userRequest);
        if (!userRepository.existsByLogin(user.getLogin())) {
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        } else {
            throw new UserAlreadyExistsException();
        }

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public AppUser update(SaveUserRequest userRequest) {
        preChangeAuthorizationCheck(userRequest.getRole());

        AppUser user = new AppUser(userRequest);
        if (!userRepository.existsByLogin(user.getLogin())) {
            throw new UserNotFound();
        }

        if (user.getPassword() != null) {
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteById(Integer userId) {
        UserRole role = userRepository.findUserRoleById(userId);
        preChangeAuthorizationCheck(role);

        userRepository.deleteById(userId);
    }

    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN', 'TEACHER', 'STUDENT')")
    public List<AppUser> findAllUsers() {
        return userRepository.findAll();
    }
}
