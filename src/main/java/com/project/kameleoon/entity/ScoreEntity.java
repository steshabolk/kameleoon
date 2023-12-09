package com.project.kameleoon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "scores")
public class ScoreEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "quote_id", referencedColumnName = "id")
    private QuoteEntity quote;

    @Column(name = "score")
    private Integer score;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
