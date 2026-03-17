package batial.fintrack.budget;

import batial.fintrack.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getByPeriod(
            @RequestParam Integer month,
            @RequestParam Integer year,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                budgetService.findByUserAndPeriod(user, month, year)
                        .stream()
                        .map(BudgetResponse::from)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<BudgetResponse> create(@RequestBody Budget budget,
                                                 @AuthenticationPrincipal User user) {
        budget.setUser(user);
        Budget saved = budgetService.save(budget);
        return ResponseEntity.ok(BudgetResponse.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        budgetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}