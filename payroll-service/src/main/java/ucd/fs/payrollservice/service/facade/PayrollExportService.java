package ucd.fs.payrollservice.service.facade;

import java.io.IOException;
import java.io.OutputStream;

public interface PayrollExportService {

    public void exportPdf(OutputStream out);
    public void exportExcel(OutputStream out) throws IOException;
}
