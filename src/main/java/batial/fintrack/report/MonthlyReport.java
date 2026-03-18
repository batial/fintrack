package batial.fintrack.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public record MonthlyReport(
        BigDecimal totalIncome, //total ingresos por mes
        BigDecimal totalExpense, //total de gastos por mes
        BigDecimal balance, //saldo. positivo o negativo
        Map<String, BigDecimal> expensesByCategory // mapa gastos por categoria
) implements Serializable {}