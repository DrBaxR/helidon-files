package com.example.app;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.MultiPart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    @GET
    @Path("/{fileId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("fileId") String fileId) {
        Optional<File> fileOptional = storageService.getFromStorage(fileId);

        if (fileOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        File file = fileOptional.get();
        String originalName = storageService.getOriginalFileName(file.getName());

        return Response
                .ok(file)
                .header("Content-Disposition", "attachment; filename=\"" + originalName + "\"")
                .build();
    }
}
