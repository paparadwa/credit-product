package starbank.credit_product_recommendation.service;

import org.springframework.stereotype.Service;
import starbank.credit_product_recommendation.model.RecommendationDto;
import starbank.credit_product_recommendation.rules.Invest500RuleSet;
import starbank.credit_product_recommendation.rules.RecommendationRuleSet;
import starbank.credit_product_recommendation.rules.SimpleCreditRuleSet;
import starbank.credit_product_recommendation.rules.TopSavingRuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationService(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    public List<RecommendationDto> getRecommendationsForUser(UUID id) {
        List<RecommendationDto> recommendations = new ArrayList<>();
        for (RecommendationRuleSet rule : ruleSets) {
            Optional<RecommendationDto> recommendation = rule.check(id);
            recommendation.ifPresent(recommendations::add);
        }
        return recommendations;
    }
}