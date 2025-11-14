package br.com.offer.infra.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "gcs")
public record BucketConfig(String bucketName, String subdirectory) {
}
