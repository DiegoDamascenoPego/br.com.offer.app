package br.com.offer.app.infra.openapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "springdoc.info")
public class SpringdocInfoProperties {

    private String title;
    private String description;
}
