package com.project.kameleoon.dao;

import com.project.kameleoon.dto.QuoteDto;
import com.project.kameleoon.dto.UserSimpleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class QuoteDao {

    private final JdbcTemplate jdbcTemplate;

    public QuoteDto getRandomQuote() {
        String sql = generateSql(OrderBy.RANDOM, 1);
        return toQuoteDto(sql);
    }

    public List<QuoteDto> getTop10Quotes() {
        String sql = generateSql(OrderBy.DESC, 10);
        return toQuoteDtoList(sql);
    }

    public List<QuoteDto> getFlop10Quotes() {
        String sql = generateSql(OrderBy.ASC, 10);
        return toQuoteDtoList(sql);
    }

    private List<QuoteDto> toQuoteDtoList(String sql) {
        return jdbcTemplate.query(sql, (rs, rowNum) -> new QuoteDto(
                rs.getLong("quote_id"),
                new UserSimpleDto(
                        rs.getLong("user_id"),
                        rs.getString("user_name")
                ),
                rs.getString("quote"),
                rs.getInt("score")
        ));
    }

    private QuoteDto toQuoteDto(String sql) {
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new QuoteDto(
                rs.getLong("quote_id"),
                new UserSimpleDto(
                        rs.getLong("user_id"),
                        rs.getString("user_name")
                ),
                rs.getString("quote"),
                rs.getInt("score")
        ));
    }

    private String generateSql(OrderBy orderBy, int limit) {
        String sql = "SELECT q.id AS quote_id, q.user_id, u.name AS user_name, q.quote, s.score " +
                "FROM quotes q " +
                "JOIN users u ON q.user_id = u.id " +
                "LEFT JOIN " +
                "(SELECT quote_id, MAX(updated_at) AS latest_update " +
                "FROM scores " +
                "GROUP BY quote_id) curr_score " +
                "ON q.id = curr_score.quote_id " +
                "LEFT JOIN scores s ON q.id = s.quote_id AND s.updated_at = curr_score.latest_update ";

        String orderByCondition = "ORDER BY " +
                switch (orderBy) {
                    case RANDOM -> "RANDOM() ";
                    case ASC -> "score ASC ";
                    case DESC -> "score DESC ";
                };

        String limitCondition = "LIMIT " + limit;

        return sql + orderByCondition + limitCondition;
    }
}
