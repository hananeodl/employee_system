package ucd.fs.payrollservice.util;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import ucd.fs.payrollservice.bean.Payroll;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
public class ExcelGeneratorUtil {

    public void generatePayrollExcel(List<Payroll> payrolls, OutputStream out) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Fiches de paie");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Employé");
        header.createCell(1).setCellValue("Salaire");
        header.createCell(2).setCellValue("Date");

        int rowNum = 1;
        for (Payroll payroll : payrolls) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(payroll.getEmployeeId());
            row.createCell(1).setCellValue(payroll.getBaseSalary());
            row.createCell(2).setCellValue(payroll.getPaymentDate().toString());
        }

        workbook.write(out);
        workbook.close();
    }

}

