package org.genta.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.genta.DTO.ImportExcelDTO;
import org.genta.model.item;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ImportService {
    @Transactional
    public Response ImportExcel(ImportExcelDTO request) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.file);
        XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream);
        //mengambil sheet pertama
        XSSFSheet sheet = workbook.getSheetAt(0);
        //menghapus baris pertama dimana label tabel ditempatkan
        sheet.removeRow(sheet.getRow(0));

        List<item> toPersist = new ArrayList<>();

        for(Row row : sheet){
            item baris = new item();
            baris.name = row.getCell(0).getStringCellValue();
            baris.count = Long.valueOf((long) row.getCell(1).getNumericCellValue());
            baris.price = Long.valueOf(row.getCell(2).getStringCellValue());
            baris.type = row.getCell(3).getStringCellValue();
            baris.description = row.getCell(4).getStringCellValue();
            baris.createdAt = row.getCell(5).getLocalDateTimeCellValue();
            baris.updateAt = row.getCell(6).getLocalDateTimeCellValue();
            toPersist.add(baris);
        }

        item.persist(toPersist);
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @Transactional
    public Response ImportCSV(ImportExcelDTO request) throws IOException, CsvValidationException {
        File file = File.createTempFile("temp", "");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(request.file);

        // deklarasi CSVReader
        CSVReader reader = new CSVReader(new FileReader(file));
        String[] nextline;
        // skip baris 1 karena baris 1 merupakan label
        reader.skip(1);

        List<item> toPersist = new ArrayList<>();

        while ((nextline = reader.readNext()) != null) {
            item baris = new item();
            baris.name = nextline[0];
            baris.count = Long.valueOf(nextline[1]);
            baris.price = Long.valueOf(nextline[2]);
            baris.type = nextline[3];
            baris.description = nextline[4];
            baris.createdAt = LocalDateTime.parse(nextline[5]);
            baris.updateAt = LocalDateTime.parse(nextline[6]);
            toPersist.add(baris);
        }
        item.persist(toPersist);
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }
}
