package com.renanresende.bridgetotalk.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Cadastro de Vendedores - Desafio Casas Bahia")
                        .description("BridgetoTalk is a modern, scalable, multi-tenant backend application designed to centralize and manage customer \n" +
                                "service conversations across various external channels (WhatsApp, Telegram, etc.). It serves as a comprehensive portfolio \n" +
                                "project demonstrating professional software architecture, domain modeling, and cloud-native readiness")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Renan Resende")
                                .email("rmnresende@gmail.com")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório no GitHub")
                        .url("https://github.com/rmnresende/bridgetalk")
                );
    }
}
