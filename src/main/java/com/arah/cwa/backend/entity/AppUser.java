package com.arah.cwa.backend.entity;

import com.arah.cwa.backend.rest.request.SaveUserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AppUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "user_id_seq")
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "age")
    private Integer age;

    public AppUser(SaveUserRequest request) {
        if (request == null) {
            return;
        }

        this.id = request.getId();
        this.login = request.getLogin();
        this.password = request.getPassword();
        this.role = request.getRole();
        this.email = request.getEmail();
        this.firstName = request.getFirstName();
        this.secondName = request.getSecondName();
        this.age = request.getAge();
    }

}
