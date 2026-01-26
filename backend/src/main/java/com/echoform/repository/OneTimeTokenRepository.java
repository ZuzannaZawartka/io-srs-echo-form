package com.echoform.repository;

import com.echoform.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OneTimeTokenRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<TokenResponse> findTokensByFormId(Long formId) {
        String formPrincipal = "form:" + formId;
        String sql = "SELECT * FROM one_time_tokens WHERE username = ?";
        
        return jdbcTemplate.query(sql, (rs, rowNum) -> new TokenResponse(
            rs.getString("token_value"),
            formId,
            rs.getTimestamp("expires_at").toLocalDateTime(),
            LocalDateTime.now()
        ), formPrincipal);
    }
    public java.util.Optional<String> findUsernameByToken(String tokenValue) {
        String sql = "SELECT username FROM one_time_tokens WHERE token_value = ?";
        try {
            String username = jdbcTemplate.queryForObject(sql, String.class, tokenValue);
            return java.util.Optional.ofNullable(username);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return java.util.Optional.empty();
        }
    }
}
