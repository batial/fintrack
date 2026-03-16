package batial.fintrack.transaction;

import batial.fintrack.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                transactionService.findByUser(user)
                        .stream()
                        .map(TransactionResponse::from)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody Transaction transaction,
                                                      @AuthenticationPrincipal User user) {
        transaction.setUser(user);
        Transaction saved = transactionService.save(transaction);
        return ResponseEntity.ok(TransactionResponse.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}