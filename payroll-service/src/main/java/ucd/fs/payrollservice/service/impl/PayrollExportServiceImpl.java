package ucd.fs.payrollservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucd.fs.payrollservice.bean.Payroll;
import ucd.fs.payrollservice.dao.PayrollDao;
import ucd.fs.payrollservice.service.facade.PayrollExportService;
import ucd.fs.payrollservice.util.ExcelGeneratorUtil;
import ucd.fs.payrollservice.util.PdfGeneratorUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class PayrollExportServiceImpl implements PayrollExportService {

    @Autowired
    private PayrollDao payrollRepository;

    @Autowired
    private PdfGeneratorUtil pdfUtil;

    @Autowired
    private ExcelGeneratorUtil excelUtil;

    public void exportPdf(OutputStream out) {
        List<Payroll> payrolls = payrollRepository.findAll();
        pdfUtil.generatePayrollPdf(payrolls, out);
    }

    public void exportExcel(OutputStream out) throws IOException {
        List<Payroll> payrolls = payrollRepository.findAll();
        excelUtil.generatePayrollExcel(payrolls, out);
    }
}

