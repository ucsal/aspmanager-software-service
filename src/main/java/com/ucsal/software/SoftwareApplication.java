package com.ucsal.software;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
    title = "ASPManager API - Softwares", version = "1.0", 
    description = "Microserviço de Gestão e Solicitação de Softwares"),
    
    servers = {
        @Server(url = "http://localhost:8085", description = "Ambiente Local (Desenvolvimento)"),
        @Server(url = "http://localhost:8080/software", description = "API Gateway (Produção)")
    })
@EnableDiscoveryClient
public class SoftwareApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftwareApplication.class, args);
    }

}
