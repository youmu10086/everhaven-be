package com.cyf.everhavenbe.service.impl;

import com.cyf.everhavenbe.config.AiConfig;
import com.cyf.everhavenbe.service.AiAssistantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AiAssistantServiceImpl implements AiAssistantService {

    private final WebClient webClient;
    private final AiConfig aiConfig;
    private final ObjectMapper objectMapper;

    public AiAssistantServiceImpl(AiConfig aiConfig, ObjectMapper objectMapper) {
        this.aiConfig = aiConfig;
        this.objectMapper = objectMapper;
        this.webClient = WebClient.builder()
                .baseUrl(aiConfig.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + aiConfig.getApiKey())
                .build();
    }

    @Override
    public Flux<String> streamChat(String message) {
        log.info("Processing AI chat request: {}", message);

        // Standard OpenAI-compatible Chat Completion structure
        Map<String, Object> body = Map.of(
                "model", aiConfig.getModel(),
                "messages", List.of(
                        Map.of("role", "system", "content", "你是一个Everhaven长租公寓的智能客服，负责回答用户关于租房、设施、服务的咨询。请用温柔、专业的语气回答。"),
                        Map.of("role", "user", "content", message)
                ),
                "stream", true
        );

        return webClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .filter(data -> !data.equals("[DONE]"))
                .map(this::extractContent)
                .filter(content -> content != null && !content.isEmpty())
                .map(content -> {
                    try {
                        // Return format required by frontend: {"text": "..."}
                        return objectMapper.writeValueAsString(Map.of("text", content));
                    } catch (Exception e) {
                        return "{\"text\": \"\"}";
                    }
                })
                .doOnError(e -> log.error("AI Streaming error", e));
    }

    /**
     * Extracts content from SSE data string: data: {"choices":[{"delta":{"content":"..."}}]}
     */
    private String extractContent(String json) {
        try {
            // WebClient might already strip "data: " prefix when using bodyToFlux(String.class)
            // but we need to be careful with formatting.
            if (json.startsWith("data: ")) {
                json = json.substring(6);
            }
            Map<String, Object> res = objectMapper.readValue(json, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) res.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> delta = (Map<String, Object>) choices.get(0).get("delta");
                if (delta != null && delta.containsKey("content")) {
                    return (String) delta.get("content");
                }
            }
        } catch (Exception e) {
            // Ignore parsing errors for individual chunks
        }
        return null;
    }
}

