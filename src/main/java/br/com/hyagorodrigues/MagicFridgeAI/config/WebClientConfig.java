package br.com.hyagorodrigues.MagicFridgeAI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/interactions}")
    public String GeminiUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(GeminiUrl)
                .build();
    }
}
