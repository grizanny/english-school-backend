package com.arah.cwa.backend.rest.request;

import com.arah.cwa.backend.entity.UserRole;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserRequest {

    @NonNull
    private Integer id;

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    private UserRole role;

    @NotEmpty
    private String email;

    private String firstName;

    private String secondName;

    private Integer age;
}
