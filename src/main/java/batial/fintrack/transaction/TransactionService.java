package batial.fintrack.transaction;

import batial.fintrack.category.CategoryRepository;
import batial.fintrack.user.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Transaction> findByUser(User user) {
        return transactionRepository.findByUserOrderByDateDesc(user);
    }

    public Transaction save(Transaction transaction) {
        if (transaction.getCategory() != null && transaction.getCategory().getId() != null) {
            categoryRepository.findById(transaction.getCategory().getId())
                    .ifPresent(transaction::setCategory);
        }
        return transactionRepository.save(transaction);
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}