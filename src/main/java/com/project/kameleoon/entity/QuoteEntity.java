package com.project.kameleoon.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "quotes")
public class QuoteEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "quote")
    private String quote;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "quote", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<QuoteEntity> votes = new ArrayList<>();

    @OneToMany(mappedBy = "quote", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ScoreEntity> scores = new ArrayList<>();

    public Integer getScore() {
        return Collections.max(scores, Comparator.comparing(ScoreEntity::getUpdatedAt)).getScore();
    }
}
