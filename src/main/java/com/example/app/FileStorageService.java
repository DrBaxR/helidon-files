package com.example.app;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class FileStorageService {

    private final String STORAGE_PATH = "file_storage";
    private File storageDirectory;

    @PostConstruct
    public void init() {
        storageDirectory = new File(STORAGE_PATH);
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

    public Optional<File> getFromStorage(String uuid) {
        Optional<File> fileOptional = Optional.empty();

        for (File file : storageDirectory.listFiles()) {
            String fileName = file.getName();
            if (fileName.contains(uuid)) {
                fileOptional = Optional.of(file);
            }
        }

        return fileOptional;
    }

    public String getOriginalFileName(String storageName) {
        List<String> splitName = Arrays.stream(storageName.split("\\.")).skip(1).toList();

        return splitName.stream()
                .reduce("", (prev, curr) -> prev + "." + curr);
    }
}
