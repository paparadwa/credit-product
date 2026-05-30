package starbank.credit_product_recommendation.model;

import java.util.List;
import java.util.UUID;

public class RecommendationResponse {
    private UUID user_id;
    private List<RecommendationDto> recommendations;

    public RecommendationResponse(UUID user_id, List<RecommendationDto> recommendations) {
        this.user_id = user_id;
        this.recommendations = recommendations;
    }

    public RecommendationResponse() {
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public List<RecommendationDto> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationDto> recommendations) {
        this.recommendations = recommendations;
    }
}
