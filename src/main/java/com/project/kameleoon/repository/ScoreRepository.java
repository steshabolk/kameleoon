package com.project.kameleoon.repository;

import com.project.kameleoon.entity.ScoreEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {

    List<ScoreEntity> findByIdOrderByUpdatedAtDesc(Long id, Limit limit);
}
