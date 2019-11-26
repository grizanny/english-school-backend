package com.arah.cwa.backend.repository;

import com.arah.cwa.backend.entity.VisitLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitLogRepository extends CrudRepository<VisitLog, Integer> {
}
