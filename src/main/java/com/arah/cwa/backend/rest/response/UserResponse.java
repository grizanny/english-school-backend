package com.arah.cwa.backend.rest.response;

import com.arah.cwa.backend.entity.AppUser;
import com.arah.cwa.backend.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserResponse {

    private Integer id;

    private String login;

    private String password;

    private UserRole role;

    private String email;

    private String firstName;

    private String secondName;

    private Integer age;

    public UserResponse(AppUser user) {
        if (user == null) {
            return;
        }

        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.age = user.getAge();
    }
}
