package batial.fintrack.budget;

import batial.fintrack.category.CategoryRepository;
import batial.fintrack.user.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public BudgetService(BudgetRepository budgetRepository,
                         CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Budget> findByUserAndPeriod(User user, Integer month, Integer year) {
        return budgetRepository.findByUserAndMonthAndYear(user, month, year);
    }

    public Budget save(Budget budget) {
        if (budget.getCategory() != null && budget.getCategory().getId() != null) {
            categoryRepository.findById(budget.getCategory().getId())
                    .ifPresent(budget::setCategory);
        }
        return budgetRepository.save(budget);
    }

    public void delete(Long id) {
        budgetRepository.deleteById(id);
    }
}