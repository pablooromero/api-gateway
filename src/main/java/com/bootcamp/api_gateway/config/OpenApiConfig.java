package com.bootcamp.api_gateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Gateway",
                version = "1.0",
                description = "Documentación de las rutas del Gateway",
                contact = @Contact(name = "Pablo Romero", email = "pabloromerook@gmail.com")
        )
)
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer gatewayOpenApiCustomizer() {
        return openApi -> {
            Paths paths = new Paths();

            paths.addPathItem("/api/users/**", new PathItem()
                    .get(new Operation()
                            .tags(List.of("User Service"))
                            .summary("Obtiene usuarios")
                            .description("Ruta hacia el servicio de usuarios")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno")))));

            paths.addPathItem("/api/orders/**", new PathItem()
                    .post(new Operation()
                            .tags(List.of("Order Service"))
                            .summary("Crea órdenes")
                            .description("Ruta hacia el servicio de órdenes")
                            .responses(new ApiResponses()
                                    .addApiResponse("201", new ApiResponse().description("Creado"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno")))));

            paths.addPathItem("/api/products/**", new PathItem()
                    .get(new Operation()
                            .tags(List.of("Product Service"))
                            .summary("Obtiene productos")
                            .description("Ruta hacia el servicio de productos")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno")))));

            openApi.setPaths(paths);
        };
    }
}
