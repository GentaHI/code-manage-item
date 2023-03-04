package org.genta.services;

import org.genta.model.item;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class itemServices {

    public Response get1(){
        return Response.status(Response.Status.OK).entity(item.findAll().list()).build();
    }

    @GET
    public Response get2(@PathParam("id") Long id){
        item searchItem = item.findById(id);
        if(searchItem==null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<item> foundItem = item.find("id =?1", searchItem.id).list();
        List<Map<String, Object>> hasil = new ArrayList<>();
        for (item a : foundItem){
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.id);
            map.put("name", a.name);
            map.put("description", a.description);

            hasil.add(map);
        }
        return Response.status(Response.Status.FOUND).entity(hasil).build();
    }


    @Transactional
    public Response post(Map<String, Object> request){
        item newItem = new item();
        newItem.name = request.get("name").toString();
        newItem.count = Long.valueOf(request.get("count").toString());
        newItem.price = Long.valueOf(request.get("price").toString());
        newItem.type = request.get("type").toString();
        newItem.description = request.get("description").toString();

        newItem.persist();
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }


    @Transactional
    public Response put(Long id, Map<String, Object> request){
        item updateItem = item.findById(id);
        if(updateItem==null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        updateItem.name = request.get("name").toString();
        updateItem.count = Long.valueOf(request.get("count").toString());
        updateItem.price = Long.valueOf(request.get("price").toString());
        updateItem.type = request.get("type").toString();
        updateItem.description = request.get("description").toString();

        updateItem.persist();
        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }



    @Transactional
    public Response del(Long id){
        item delItem = item.findById(id);
        if(delItem==null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        delItem.delete();
        return Response.status(Response.Status.OK).entity(new HashMap<>()).build();
    }
}
