package batial.fintrack.budget;

import java.math.BigDecimal;

public record BudgetResponse(
        Long id,
        BigDecimal amount,
        Integer month,
        Integer year,
        String categoryName
) {
    public static BudgetResponse from(Budget budget) {
        return new BudgetResponse(
                budget.getId(),
                budget.getAmount(),
                budget.getMonth(),
                budget.getYear(),
                budget.getCategory().getName()
        );
    }
}