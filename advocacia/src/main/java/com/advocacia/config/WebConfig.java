package com.advocacia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Configuração global do Spring MVC.
 * Define mapeamentos de recursos estáticos e outras configurações web.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura os recursos estáticos (CSS, JS, imagens).
     * Spring Boot já faz isso automaticamente, mas aqui podemos customizar.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * Configura redirecionamentos de URL simples (sem necessidade de criar controllers)
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redireciona /inicio para /
        registry.addRedirectViewController("/inicio", "/");
    }
}
