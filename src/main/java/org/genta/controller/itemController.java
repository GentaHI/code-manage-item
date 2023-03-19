package org.genta.controller;


import com.opencsv.exceptions.CsvValidationException;
import net.sf.jasperreports.engine.JRException;
import org.genta.DTO.ImportExcelDTO;
import org.genta.services.ExportService;
import org.genta.services.ImportService;
import org.genta.services.itemServices;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class itemController {
    @Inject
    itemServices itemservices;

    @Inject
    ExportService exportService;

    @Inject
    ImportService importService;

    @GET
    public Response get1(){
        return itemservices.get1();
    }

    @GET
    @Path("/{id}")
    public Response get2(@PathParam("id") Long id){
        return itemservices.get2(id);
    }

    @GET
    @Path("/export/pdf")
    @Produces("application/pdf")
    public Response exportPDF() throws JRException {
        return exportService.exportPDF();
    }

    @GET
    @Path("/export/excel")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response exportExcel() throws IOException {
        return exportService.exportExcel();
    }

    @GET
    @Path("/export/csv")
    @Produces("text/csv")
    public Response exportCSV() throws IOException {
        return exportService.exportCSV();
    }

    @POST
    public Response post(Map<String, Object> request){
        return itemservices.post(request);
    }

    @POST
    @Path("/import/excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importExcel(@MultipartForm ImportExcelDTO file) throws IOException {
        return importService.ImportExcel(file);
    }

    @POST
    @Path("/import/csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importCSV(@MultipartForm ImportExcelDTO file) throws CsvValidationException, IOException {
        return importService.ImportCSV(file);
    }

    @PUT
    @Path("/{id}")
    public Response put (@PathParam("id") Long id, Map<String, Object> request){
        return itemservices.put(id, request);
    }
    @DELETE
    @Path("/{id}")
    public Response del (@PathParam("id") Long id){
        return itemservices.del(id);
    }
}
