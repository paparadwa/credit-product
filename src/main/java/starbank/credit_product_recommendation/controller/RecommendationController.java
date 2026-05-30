package starbank.credit_product_recommendation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import starbank.credit_product_recommendation.model.RecommendationResponse;
import starbank.credit_product_recommendation.service.RecommendationService;

import java.util.UUID;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendation/{user_id}")
    public ResponseEntity<RecommendationResponse> getRecommendationsForUser(@PathVariable UUID user_id) {
        RecommendationResponse recommendationResponse = new RecommendationResponse();
        recommendationResponse.setUser_id(user_id);
        recommendationResponse.setRecommendations(recommendationService.getRecommendationsForUser(user_id));
        return ResponseEntity.ok(recommendationResponse);
    }
}
