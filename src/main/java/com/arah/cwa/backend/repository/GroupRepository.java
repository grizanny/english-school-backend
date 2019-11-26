package com.arah.cwa.backend.repository;

import com.arah.cwa.backend.entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {
}
