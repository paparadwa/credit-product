package starbank.credit_product_recommendation.rules;

import starbank.credit_product_recommendation.model.RecommendationDto;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<RecommendationDto> check(UUID userId);
}