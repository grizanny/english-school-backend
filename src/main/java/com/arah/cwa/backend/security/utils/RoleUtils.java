package com.arah.cwa.backend.security.utils;

import com.arah.cwa.backend.entity.UserRole;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;

import java.util.Collection;

@UtilityClass
public class RoleUtils {

    public void preSaveCheck(UserRole userRole) {
        Collection<? extends GrantedAuthority> roles = getRoles();

        boolean contains = false;
        for (GrantedAuthority authority : roles) {
            if (authority.getAuthority().equals(userRole.name())) {
                contains = true;
                break;
            }
        }

        if (!contains) {
            throw new UnauthorizedUserException("Unauthorized access");
        }
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities();
    }
}
