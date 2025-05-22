package ucd.fs.payrollservice.util;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Component;
import ucd.fs.payrollservice.bean.Payroll;

import java.io.OutputStream;
import java.util.List;

@Component
public class PdfGeneratorUtil {
    public void generatePayrollPdf(List<Payroll> payrollList, OutputStream out) {
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Liste des fiches de paie"));

        for (Payroll payroll : payrollList) {
            document.add(new Paragraph("Employé: " + payroll.getId()));
            document.add(new Paragraph("Salaire: " + payroll.getBaseSalary()));
            document.add(new Paragraph("Date: " + payroll.getPaymentDate().toString()));
            document.add(new Paragraph("----------------------------"));
        }

        document.close();
    }
}

