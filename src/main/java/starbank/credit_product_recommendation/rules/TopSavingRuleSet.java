package starbank.credit_product_recommendation.rules;

import org.springframework.stereotype.Component;
import starbank.credit_product_recommendation.model.RecommendationDto;
import starbank.credit_product_recommendation.repository.RecommendationsRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRuleSet implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;
    private final UUID id = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");
    private final String name = "Top Saving";
    private final String text = "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!\n" +
            "\n" +
            "Преимущества «Копилки»:\n" +
            "\n" +
            "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.\n" +
            "\n" +
            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.\n" +
            "\n" +
            "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.\n" +
            "\n" +
            "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!";

    public TopSavingRuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {
        boolean hasDebit = recommendationsRepository.hasProductOfType(userId, "DEBIT");
        BigDecimal sumDebitDeposits = recommendationsRepository.sumDepositByType(userId, "DEBIT");
        BigDecimal sumSavingDeposits = recommendationsRepository.sumDepositByType(userId, "SAVING");
        BigDecimal sumDebitWithdraws = recommendationsRepository.sumWithdrawByType(userId, "DEBIT");
        if (hasDebit && ((sumDebitDeposits.compareTo(BigDecimal.valueOf(50000))) > 0 || (sumSavingDeposits.compareTo(BigDecimal.valueOf(50000))) > 0) && sumDebitDeposits.compareTo(sumDebitWithdraws) > 0) {
            return Optional.of(new RecommendationDto(id, name, text));
        }
        return Optional.empty();
    }
}