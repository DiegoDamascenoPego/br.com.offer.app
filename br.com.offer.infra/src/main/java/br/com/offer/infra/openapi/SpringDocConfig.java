package br.com.offer.infra.openapi;

import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(value = { SpringdocInfoProperties.class })
public class SpringDocConfig {

    private final SpringdocInfoProperties infoProperties;

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(this.getInfo())
            .externalDocs(new ExternalDocumentation()
                .description("Documentação completa")
                .url("https://github.com/DiegoDamascenoPego/br.com.offer.app"))
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
            .components(new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER)
                    .name("Authorization")
                )
            );
    }

    @Bean
    public GroupedOpenApi usuariosApis() {
        return GroupedOpenApi.builder()
            .group("Usuários")
            .pathsToMatch("/api/v1/usuarios/**") // Include paths starting with /public
            .build();
    }

    @Bean
    public GroupedOpenApi categoriasApi() {
        return GroupedOpenApi.builder()
            .group("Categoria")
            .pathsToMatch("/api/v1/categorias/**") // Include paths starting with /public
            .build();
    }

    @Bean
    public GroupedOpenApi catalogoApi() {
        return GroupedOpenApi.builder()
            .group("Catálogo")
            .pathsToMatch("/api/catalogos/**") // Include paths starting with /public
            .build();
    }

    @Bean
    public GroupedOpenApi leilaoApi() {
        return GroupedOpenApi.builder()
            .group("Leilão")
            .pathsToMatch("/api/v1/leiloes/**") // Include paths starting with /public
            .build();
    }

    private Info getInfo() {
        return new Info()
            .version("1.0.0")
            .title(this.infoProperties.getTitle())
            .description(this.infoProperties.getDescription())
            .version("1.0.0")
            .license(new License()
                .name("Apache 2.0")
                .url("http://springdoc.org"));
    }

    @Bean
    public GlobalOpenApiCustomizer customGlobalResponses() {
        return openApi -> {
            // Primeiro registra o schema ErrorDetails nos componentes
            if (openApi.getComponents() == null) {
                openApi.setComponents(new io.swagger.v3.oas.models.Components());
            }

            // Cria o schema baseado na classe ErrorDetails
            Schema<?> errorDetailsSchema = new Schema<ErrorDetails>()
                .type("object")
                .name("ErrorDetails")
                .description("Detalhes sobre um erro ocorrido na API")
                .addProperty("code", new Schema<String>().type("string").description("Código do erro"))
                .addProperty("message", new Schema<String>().type("string").description("Mensagem do erro"))
                .addProperty("detailedMessage", new Schema<String>().type("string").description("Mensagem detalhada do erro"))
                .addProperty("type", new Schema<String>().type("string").description("Tipo do erro"));

            openApi.getComponents().addSchemas("ErrorDetails", errorDetailsSchema);

            // Depois adiciona as respostas globais
            openApi.getPaths().values().forEach(pathItem -> {
                pathItem.readOperations().forEach(operation -> {
                    ApiResponses responses = operation.getResponses();
                    if (responses == null) {
                        responses = new ApiResponses();
                        operation.setResponses(responses);
                    }

                    // Sempre adiciona 200 se não existir
                    if (responses.get("200") == null) {
                        responses.addApiResponse("200", new ApiResponse().description("OK"));
                    }

                    // Sempre adiciona 401 e 403 (autenticação/autorização são globais)
                    if (responses.get("401") == null) {
                        responses.addApiResponse("401", new ApiResponse().description("Usuário não autorizado")
                            .content(new Content().addMediaType("*/*",
                                new MediaType().schema(new Schema<>().type("object")))));
                    }

                    if (responses.get("403") == null) {
                        responses.addApiResponse("403", new ApiResponse().description("Requisição não autorizada")
                            .content(new Content().addMediaType("*/*",
                                new MediaType().schema(new Schema<>().type("object")))));
                    }

                    // Adiciona 400 apenas para operações POST, PUT, PATCH (que recebem dados)
                    String httpMethod = getHttpMethod(operation, pathItem);
                    if (("POST" .equals(httpMethod) || "PUT" .equals(httpMethod) || "PATCH" .equals(httpMethod))
                        && responses.get("400") == null) {
                        responses.addApiResponse("400", createErrorResponse("Parâmetros inválidos"));
                    }

                    // Adiciona 404 apenas para operações GET, PUT, DELETE que usam ID (operações específicas)
                    if (("GET" .equals(httpMethod) || "PUT" .equals(httpMethod) || "DELETE" .equals(httpMethod))
                        && hasPathParameter(pathItem) && responses.get("404") == null) {
                        responses.addApiResponse("404", createErrorResponse("Recurso não encontrado"));
                    }

                    // 500 é sempre válido (erro interno do servidor pode acontecer em qualquer operação)
                    if (responses.get("500") == null) {
                        responses.addApiResponse("500", createErrorResponse("Erro do Servidor interno"));
                    }
                });
            });
        };
    }

    private String getHttpMethod(io.swagger.v3.oas.models.Operation operation, io.swagger.v3.oas.models.PathItem pathItem) {
        if (pathItem.getGet() == operation)
            return "GET";
        if (pathItem.getPost() == operation)
            return "POST";
        if (pathItem.getPut() == operation)
            return "PUT";
        if (pathItem.getDelete() == operation)
            return "DELETE";
        if (pathItem.getPatch() == operation)
            return "PATCH";
        return "UNKNOWN";
    }

    private boolean hasPathParameter(io.swagger.v3.oas.models.PathItem pathItem) {
        // Verifica se o path tem parâmetros (contém {})
        return pathItem.toString().contains("{") && pathItem.toString().contains("}");
    }

    private ApiResponse createErrorResponse(String description) {
        return new ApiResponse()
            .description(description)
            .content(new Content()
                .addMediaType("application/json",
                    new MediaType()
                        .schema(new Schema<ErrorDetails>()
                            .name("ErrorDetails")
                            .$ref("#/components/schemas/ErrorDetails"))));
    }
}
