package org.genta.services;

import com.opencsv.CSVWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.genta.model.item;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

    public ByteArrayOutputStream resultOuputExcel() throws IOException {
        List<item> itemList = item.listAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheets= workbook.createSheet("data_items");

        int rownum = 0;
        Row row = sheets.createRow(rownum++);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("nama_barang");
        row.createCell(2).setCellValue("count");
        row.createCell(3).setCellValue("harga");
        row.createCell(4).setCellValue("deskripsi");
        row.createCell(5).setCellValue("created_at");
        row.createCell(6).setCellValue("update_at");

        for (item baris : itemList){
            row = sheets.createRow(rownum++);
            row.createCell(0).setCellValue(baris.id);
            row.createCell(1).setCellValue(baris.name);
            row.createCell(2).setCellValue(baris.count);
            row.createCell(3).setCellValue(baris.price);
            row.createCell(4).setCellValue(baris.description);
            row.createCell(5).setCellValue(baris.createdAt);
            row.createCell(6).setCellValue(baris.updateAt);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream;
    }

    public Response exportExcel() throws IOException {

        ByteArrayOutputStream outputStream = resultOuputExcel();

        return Response.ok()
                .type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-Dispotition","attachment; filename=\"list_item_excel.xlsx\"")
                .entity(outputStream.toByteArray()).build();
    }

    public Response exportCSV() throws IOException {
        File file = File.createTempFile("temp","");

        FileWriter fileWriter = new FileWriter(file);

        CSVWriter writer = new CSVWriter(fileWriter);

        String[] headers = {"id","nama_barang",
                "count","harga","deskripsi",
                "create_at", "update_at"
        };
        writer.writeNext(headers);

        List<item> itemList = item.listAll();
        for (item baris : itemList){
            String[] dataBaris ={
                    baris.id.toString(),
                    baris.name,
                    baris.count.toString(),
                    baris.price.toString(),
                    baris.description,
                    baris.createdAt.toString(),
                    baris.updateAt.toString()
            };
            writer.writeNext(dataBaris);
        }
        return Response.ok()
                .type("text/csv")
                .header("Content-Dispotition","attachment; filename=\"list_items_csv.csv\"")
                .entity(file).build();
    }
}
