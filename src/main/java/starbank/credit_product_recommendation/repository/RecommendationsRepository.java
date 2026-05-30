package starbank.credit_product_recommendation.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean hasProductOfType(UUID userId, String productType) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ?",
                Integer.class,
                userId,
                productType);
        return count != null && count > 0;
    }

    public BigDecimal sumDepositByType(UUID userId, String productType) {
        BigDecimal sum = jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(amount), 0) FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = 'DEPOSIT'",
                BigDecimal.class,
                userId,
                productType);
        return sum;
    }

    public BigDecimal sumWithdrawByType(UUID userId, String productType) {
        BigDecimal sum = jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(amount), 0) FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = 'WITHDRAW'",
                BigDecimal.class,
                userId,
                productType);
        return sum;
    }
}