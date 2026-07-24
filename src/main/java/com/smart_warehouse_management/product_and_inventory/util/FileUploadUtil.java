package com.smart_warehouse_management.product_and_inventory.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.smart_warehouse_management.product_and_inventory.config.FileStorageConfig;

@Component
public class FileUploadUtil {

    private final FileStorageConfig config;

    public FileUploadUtil(FileStorageConfig config) {
        this.config = config;
    }

    public String uploadFile(MultipartFile file) throws IOException {

        String uploadDir = config.getUploadDir();

        Path path = Paths.get(uploadDir);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String fileName =
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        Files.copy(file.getInputStream(),
                path.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + fileName;
    }

}