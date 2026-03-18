package batial.fintrack.report;

import batial.fintrack.transaction.Transaction;
import batial.fintrack.transaction.TransactionRepository;
import batial.fintrack.transaction.TransactionType;
import batial.fintrack.user.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Cacheable(value = "reports", key = "#user.id + '-' + #month + '-' + #year")
    public MonthlyReport getMonthlyReport(User user, Integer month, Integer year) {
        List<Transaction> transactions = transactionRepository
                .findByUserOrderByDateDesc(user);

        List<Transaction> filtered = transactions.stream()
                .filter(t -> t.getDate().getMonthValue() == month
                        && t.getDate().getYear() == year)
                .toList();

        BigDecimal totalIncome = filtered.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = filtered.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> byCategory = filtered.stream()
                .filter(t -> t.getCategory() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.reducing(BigDecimal.ZERO,
                                Transaction::getAmount, BigDecimal::add)
                ));

        return new MonthlyReport(totalIncome, totalExpense,
                totalIncome.subtract(totalExpense), byCategory);
    }
}