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

            // Rutas para User Service
            paths.addPathItem("/api/users", new PathItem()
                    .get(new Operation()
                            .tags(List.of("User Service"))
                            .summary("Obtiene usuarios")
                            .description("Obtiene todos los usuarios")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno"))))
                    .post(new Operation()
                            .tags(List.of("User Service"))
                            .summary("Crea un usuario")
                            .description("Creación de un usuario")
                            .responses(new ApiResponses()
                                    .addApiResponse("201", new ApiResponse().description("Creado"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno")))));

            paths.addPathItem("/api/users/{id}", new PathItem()
                    .put(new Operation()
                            .tags(List.of("User Service"))
                            .summary("Edita un usuario")
                            .description("Edición de un usuario")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno"))))
                    .delete(new Operation()
                            .tags(List.of("User Service"))
                            .summary("Elimina un usuario")
                            .description("Elimina un usuario")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno")))));

            // Rutas para Order Service
            paths.addPathItem("/api/orders", new PathItem()
                    .get(new Operation()
                            .tags(List.of("Order Service"))
                            .summary("Obtiene órdenes")
                            .description("Obtiene todas las órdenes")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno"))))
                    .post(new Operation()
                            .tags(List.of("Order Service"))
                            .summary("Crea una orden")
                            .description("Creación de una nueva orden")
                            .responses(new ApiResponses()
                                    .addApiResponse("201", new ApiResponse().description("Creado"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno")))));

            paths.addPathItem("/api/orders/{id}", new PathItem()
                    .put(new Operation()
                            .tags(List.of("Order Service"))
                            .summary("Edita una orden")
                            .description("Edita los detalles de una orden")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno"))))
                    .delete(new Operation()
                            .tags(List.of("Order Service"))
                            .summary("Elimina una orden")
                            .description("Elimina una orden específica")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno")))));

            // Rutas para Product Service
            paths.addPathItem("/api/products", new PathItem()
                    .get(new Operation()
                            .tags(List.of("Product Service"))
                            .summary("Obtiene productos")
                            .description("Obtiene todos los productos")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno"))))
                    .post(new Operation()
                            .tags(List.of("Product Service"))
                            .summary("Crea un producto")
                            .description("Creación de un nuevo producto")
                            .responses(new ApiResponses()
                                    .addApiResponse("201", new ApiResponse().description("Creado"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno")))));

            paths.addPathItem("/api/products/{id}", new PathItem()
                    .put(new Operation()
                            .tags(List.of("Product Service"))
                            .summary("Edita un producto")
                            .description("Edita los detalles de un producto")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno"))))
                    .delete(new Operation()
                            .tags(List.of("Product Service"))
                            .summary("Elimina un producto")
                            .description("Elimina un producto específico")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Éxito"))
                                    .addApiResponse("500", new ApiResponse().description("Error interno")))));

            openApi.setPaths(paths);
        };
    }
}
