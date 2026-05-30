package starbank.credit_product_recommendation.rules;

import org.springframework.stereotype.Component;
import starbank.credit_product_recommendation.model.RecommendationDto;
import starbank.credit_product_recommendation.repository.RecommendationsRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRuleSet implements RecommendationRuleSet {
    private final RecommendationsRepository recommendationsRepository;
    private final UUID id = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");
    private final String name = "Простой кредит";
    private final String text = "Откройте мир выгодных кредитов с нами!\n" +
            "\n" +
            "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.\n" +
            "\n" +
            "Почему выбирают нас:\n" +
            "\n" +
            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\n" +
            "\n" +
            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" +
            "\n" +
            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.\n" +
            "\n" +
            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!";

    public SimpleCreditRuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {
        boolean hasCredit = recommendationsRepository.hasProductOfType(userId, "CREDIT");
        BigDecimal sumDebitDeposits = recommendationsRepository.sumDepositByType(userId, "DEBIT");
        BigDecimal sumDebitWithdraws = recommendationsRepository.sumWithdrawByType(userId, "DEBIT");
        if (!hasCredit && (sumDebitDeposits.compareTo(sumDebitWithdraws)) > 0 && sumDebitWithdraws.compareTo(BigDecimal.valueOf(100000)) > 0) {
            return Optional.of(new RecommendationDto(id, name, text));
        }
        return Optional.empty();
    }
}