package com.project.kameleoon.repository;

import com.project.kameleoon.entity.QuoteEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {

    @Query("SELECT q FROM QuoteEntity q LEFT JOIN FETCH q.user LEFT JOIN FETCH q.scores WHERE q.user.id = :userId")
    List<QuoteEntity> findAllWithScoresByUserId(@Param("userId") Long userId);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"scores", "user"})
    Optional<QuoteEntity> findWithScoresById(Long id);
}
