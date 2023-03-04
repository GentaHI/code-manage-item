package org.genta.controller;


import org.genta.services.itemServices;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Objects;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class itemController {
    @Inject
    itemServices itemservices;

    @GET
    public Response get1(){
        return itemservices.get1();
    }

    @GET
    @Path("/{id}")
    public Response get2(@PathParam("id") Long id){
        return itemservices.get2(id);
    }
    @POST
    public Response post(Map<String, Object> request){
        return itemservices.post(request);
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
