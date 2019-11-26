package com.arah.cwa.backend.repository;

import com.arah.cwa.backend.entity.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Integer> {
}
