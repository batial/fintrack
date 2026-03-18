package batial.fintrack.report;

import batial.fintrack.transaction.Transaction;
import batial.fintrack.transaction.TransactionRepository;
import batial.fintrack.transaction.TransactionType;
import batial.fintrack.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void getMonthlyReport_shouldCalculateCorrectBalance() {
        // Arrange
        User user = new User();

        Transaction income = new Transaction();
        income.setAmount(new BigDecimal("50000"));
        income.setType(TransactionType.INCOME);
        income.setDate(LocalDate.of(2026, 3, 1));

        Transaction expense = new Transaction();
        expense.setAmount(new BigDecimal("10000"));
        expense.setType(TransactionType.EXPENSE);
        expense.setDate(LocalDate.of(2026, 3, 15));

        when(transactionRepository.findByUserOrderByDateDesc(user))
                .thenReturn(List.of(income, expense));

        // Act
        MonthlyReport report = reportService.getMonthlyReport(user, 3, 2026);

        // Assert
        assertEquals(new BigDecimal("50000"), report.totalIncome());
        assertEquals(new BigDecimal("10000"), report.totalExpense());
        assertEquals(new BigDecimal("40000"), report.balance());
    }

    @Test
    void getMonthlyReport_shouldReturnZeroWhenNoTransactions() {
        // Arrange
        User user = new User();

        when(transactionRepository.findByUserOrderByDateDesc(user))
                .thenReturn(List.of());

        // Act
        MonthlyReport report = reportService.getMonthlyReport(user, 3, 2026);

        // Assert
        assertEquals(BigDecimal.ZERO, report.totalIncome());
        assertEquals(BigDecimal.ZERO, report.totalExpense());
        assertEquals(BigDecimal.ZERO, report.balance());
    }
}