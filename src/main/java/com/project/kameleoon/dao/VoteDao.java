package com.project.kameleoon.dao;

import com.project.kameleoon.dto.QuoteSimpleDto;
import com.project.kameleoon.dto.VoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class VoteDao {

    private final JdbcTemplate jdbcTemplate;

    public List<VoteDto> getVotesByUserId(Long userId) {
        String sql = "SELECT v.id AS vote_id, q.id AS quote_id, q.quote AS quote, s.score AS score, v.created_at AS vote_created_at, v.vote AS vote " +
                "FROM votes v " +
                "JOIN quotes q ON v.quote_id = q.id " +
                "LEFT JOIN " +
                "(SELECT quote_id, MAX(updated_at) AS latest_update " +
                "FROM scores " +
                "GROUP BY quote_id) curr_score " +
                "ON q.id = curr_score.quote_id " +
                "LEFT JOIN scores s ON q.id = s.quote_id AND s.updated_at = curr_score.latest_update " +
                "WHERE v.user_id = ? " +
                "ORDER BY v.created_at DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new VoteDto(
                rs.getLong("vote_id"),
                new QuoteSimpleDto(
                        rs.getLong("quote_id"),
                        rs.getString("quote"),
                        rs.getInt("score")
                ),
                rs.getObject("vote_created_at", LocalDateTime.class),
                rs.getInt("vote")
        ), userId);
    }
}
