package ucd.fs.payrollservice.ws.rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucd.fs.payrollservice.service.facade.PayrollExportService;

import java.io.IOException;

@RestController
@RequestMapping("/api/payroll/export")
public class PayrollExportController {

    @Autowired
    private PayrollExportService exportService;

    @Autowired
    private PayrollExportService payrollExportService;

    @GetMapping("/pdf")
    public void exportPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=payroll_report.pdf");
        exportService.exportPdf(response.getOutputStream());
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=payroll_report.xlsx");
        exportService.exportExcel(response.getOutputStream());
    }

    @GetMapping("/export/pdf")
    public void downloadPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=payrolls.pdf");

        payrollExportService.exportPdf(response.getOutputStream());
    }
}

