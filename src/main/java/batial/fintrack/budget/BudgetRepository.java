package batial.fintrack.budget;

import batial.fintrack.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserAndMonthAndYear(User user, Integer month, Integer year);
}