package br.com.offer.infra.storage;

import java.io.IOException;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;

public interface StorageService {

    String upload(MultipartFile file) throws IOException;

    void delete(String fileName);

    Blob getFile(String fileName);

    String signUrl(@NotNull UUID fileName, @NotNull String folder, @NotNull String typeFile);

}
