package com.advocacia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot
 * Ponto de entrada do servidor web
 */
@SpringBootApplication
public class AdvocaciaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvocaciaApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  Advocacia Imobiliária - Online!");
        System.out.println("  Acesse: http://localhost:8080");
        System.out.println("========================================\n");
    }
}
