package batial.fintrack.report;

import batial.fintrack.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/summary")
    public ResponseEntity<MonthlyReport> getMonthlySummary(
            @RequestParam Integer month,
            @RequestParam Integer year,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reportService.getMonthlyReport(user, month, year));
    }
}