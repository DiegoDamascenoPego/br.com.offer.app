package br.com.offer.infra.storage;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class StorageServiceImpl implements StorageService {

    private final Storage storage;

    private final BucketConfig bucketConfig;

    @Override
    public String upload(final MultipartFile file) throws IOException {
        String fileName = bucketConfig.subdirectory() + "/" + file.getOriginalFilename();
        var blob = storage.create(
            BlobInfo.newBuilder(bucketConfig.bucketName(), fileName).build(),
            file.getBytes()
        );
        return blob.getMediaLink();
    }

    @Override
    public void delete(final String fileName) {

    }

    @Override
    public Blob getFile(final String fileName) {
        return null;
    }

    @Override
    public String signUrl(final UUID fileName, final String folder, final String typeFile) {
        return "";
    }
}
