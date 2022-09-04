package com.example.app;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.MultiPart;

@Path("/file")
public class FileResource {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(MultiPart multiPart) {
        return Response.status(Response.Status.CREATED).build();
    }
}
