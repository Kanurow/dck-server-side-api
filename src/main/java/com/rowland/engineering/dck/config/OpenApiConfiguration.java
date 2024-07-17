package com.rowland.engineering.dck.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Rowland Kanu",
                        email = "Kanurowland92@gmail.com"
                ),
                description = "Documentation for Server Side Dominion City Kubwa application",
                title = "DCK Doc - Rowland",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local Development Environment",
                        url = "https://dck-web-application.onrender.com"
                ),
                @Server(
                        description = "Production Environment",
                        url = "https://dck-web-application.onrender.com"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
}
