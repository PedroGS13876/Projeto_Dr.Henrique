package com.advocacia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Teste de contexto da aplicação Spring Boot.
 * Verifica se todos os beans são carregados corretamente.
 */
@SpringBootTest
class AdvocaciaApplicationTests {

    @Test
    void contextLoads() {
        // Testa se o contexto Spring carrega sem erros
        System.out.println("✅ Contexto Spring Boot carregado com sucesso!");
    }
}
