package org.genta.DTO;

import javax.ws.rs.FormParam;

public class ImportExcelDTO {
    @FormParam("file")
    public byte[] file;
}
