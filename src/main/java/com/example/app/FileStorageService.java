package com.example.app;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@ApplicationScoped
public class FileStorageService {

    private final String STORAGE_PATH = "file_storage";

    @PostConstruct
    public void init() {
        File storageDirectory = new File(STORAGE_PATH);
        if (!storageDirectory.exists()){
            storageDirectory.mkdirs();
        }
    }

    public String saveToStorage(byte[] data, String fileName) {
        UUID uuid = UUID.randomUUID();
        String storedFilePath = STORAGE_PATH + "/" + uuid + "." + fileName;

        File storageFile = new File(storedFilePath);
        try (FileOutputStream outputStream = new FileOutputStream(storageFile)) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uuid.toString();
    }
}
