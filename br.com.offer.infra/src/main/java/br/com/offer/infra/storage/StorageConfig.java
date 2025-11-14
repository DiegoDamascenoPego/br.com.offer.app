package br.com.offer.infra.storage;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
@EnableConfigurationProperties(BucketConfig.class)
public class StorageConfig {

    @Bean
    public Storage getStorage() {
        return StorageOptions
            .getDefaultInstance()
            .getService();
    }
}
