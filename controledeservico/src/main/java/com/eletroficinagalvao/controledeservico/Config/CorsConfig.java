package com.eletroficinagalvao.controledeservico.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*"); // Configurar para permitir qualquer origem
        config.addAllowedHeader("*");
        config.addAllowedMethod("*"); // Configurar para permitir qualquer método
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

