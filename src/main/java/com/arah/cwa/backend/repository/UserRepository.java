package com.arah.cwa.backend.repository;

import com.arah.cwa.backend.entity.AppUser;
import com.arah.cwa.backend.entity.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Integer> {

    Optional<AppUser> findAppUserByLogin(String login);

    boolean existsByLogin(String login);

    List<AppUser> findAll();

    @Query(value = "select user_role from users where id = ?1", nativeQuery = true)
    UserRole findUserRoleById(Integer id);
}
