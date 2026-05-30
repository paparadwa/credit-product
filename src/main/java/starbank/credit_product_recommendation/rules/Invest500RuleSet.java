package starbank.credit_product_recommendation.rules;

import org.springframework.stereotype.Component;
import starbank.credit_product_recommendation.model.RecommendationDto;
import starbank.credit_product_recommendation.repository.RecommendationsRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500RuleSet implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;
    private final UUID id = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");
    private final String name = "Invest 500";
    private final String text = "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
            "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. " +
            "Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. " +
            "Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. " +
            "Откройте ИИС сегодня и станьте ближе к финансовой независимости!";

    public Invest500RuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {
        boolean hasDebit = recommendationsRepository.hasProductOfType(userId, "DEBIT");
        boolean hasInvest = recommendationsRepository.hasProductOfType(userId, "INVEST");
        BigDecimal sumSavingDeposits = recommendationsRepository.sumDepositByType(userId, "SAVING");
        if (hasDebit && !hasInvest && sumSavingDeposits.compareTo(BigDecimal.valueOf(1000)) > 0) {
            return Optional.of(new RecommendationDto(id, name, text));
        }
        return Optional.empty();
    }
}