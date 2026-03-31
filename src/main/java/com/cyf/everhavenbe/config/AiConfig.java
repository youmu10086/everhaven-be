package com.cyf.everhavenbe.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.ai")
public class AiConfig {
    /**
     * AI API Key (e.g., DeepSeek or OpenAI key)
     */
    private String apiKey;

    /**
     * AI API Base URL
     */
    private String baseUrl = "https://api.deepseek.com/v1";

    /**
     * Model name
     */
    private String model = "deepseek-chat";
}

