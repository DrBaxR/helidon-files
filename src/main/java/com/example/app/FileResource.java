package com.example.app;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.MultiPart;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

@Path("/file")
public class FileResource {

    @Inject
    private FileStorageService storageService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(MultiPart multipart) {
        List<String> fileIds = new LinkedList<>();

        for (BodyPart part : multipart.getBodyParts()) {
            if ("file".equals(part.getContentDisposition().getParameters().get("name"))) {
                InputStream fileStream = part.getEntityAs(BodyPartEntity.class).getInputStream();
                String fileName = part.getContentDisposition().getFileName();

                try {
                    fileIds.add(storageService.saveToStorage(fileStream.readAllBytes(), fileName));
                    fileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return Response.status(Response.Status.CREATED).entity(fileIds).build();
    }
}
