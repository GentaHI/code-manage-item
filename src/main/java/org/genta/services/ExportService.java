package org.genta.services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.genta.model.item;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.HashMap;

@ApplicationScoped
public class ExportService {
    public Response exportPDF() throws JRException {

        //load template jesper yg sudah dibuat
        File file = new File("src/main/resources/Tugas_01.jrxml");

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(item.listAll());

        // build jasper report dari template yg telah di load
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // create jasperPrint object
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), jrBeanCollectionDataSource);

        // export jasperPrint to byte array
        byte[] jasperResult = JasperExportManager.exportReportToPdf(jasperPrint);

        return Response.ok().type("application/pdf").entity(jasperResult).build();

    }
}
