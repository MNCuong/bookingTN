package com.example.booking.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    //config JWT
    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    //config thong bao loi thi test API
    @Bean
    public OpenApiCustomizer globalResponsesOpenApiCustomiser() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                operation.getResponses().addApiResponse("400", createApiResponse("Yêu cầu không hợp lệ"));
                operation.getResponses().addApiResponse("401", createApiResponse("Không có quyền truy cập"));
                operation.getResponses().addApiResponse("500", createApiResponse("Lỗi hệ thống, vui lòng thử lại sau"));
            }));
        };
    }

    private ApiResponse createApiResponse(String message) {
        return new ApiResponse()
                .description(message)
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<>().example("""
                                    {
                                        "code": "ERROR_CODE",
                                        "message": "%s"
                                    }
                                """.formatted(message)))));
    }
}

