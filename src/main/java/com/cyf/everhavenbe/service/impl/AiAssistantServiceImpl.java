package com.cyf.everhavenbe.service.impl;

import com.cyf.everhavenbe.config.AiConfig;
import com.cyf.everhavenbe.model.dto.AiChatRequestDTO;
import com.cyf.everhavenbe.service.AiAssistantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AiAssistantServiceImpl implements AiAssistantService {

    private final WebClient webClient;
    private final AiConfig aiConfig;
    private final ObjectMapper objectMapper;

    public AiAssistantServiceImpl(AiConfig aiConfig, ObjectMapper objectMapper) {
        log.info("init AiAssistantServiceImpl");
        this.aiConfig = aiConfig;
        this.objectMapper = objectMapper;
        this.webClient = WebClient.builder()
                .baseUrl(aiConfig.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + aiConfig.getApiKey())
                .build();
    }

    @Override
    public Flux<String> streamChat(AiChatRequestDTO request) {
        log.info("Processing AI chat request to ModelScope: {}", request.getMessage());

        List<Map<String, String>> messages = new ArrayList<>();

        // 1. 系统提示词
        String systemPrompt = request.getSystemPrompt();
        if (systemPrompt == null || systemPrompt.isEmpty()) {
            systemPrompt = "你是一个Everhaven长租公寓的智能客服，负责回答用户关于租房、设施、服务的咨询。请用温柔、专业的语气回答。";
        }
        messages.add(Map.of("role", "system", "content", systemPrompt));

        // 2. 消息历史处理 - 确保 role 合法
        if (request.getHistory() != null && !request.getHistory().isEmpty()) {
            for (Map<String, String> msg : request.getHistory()) {
                String role = msg.get("role");
                String content = msg.get("content");
                // OpenAI/ModelScope 兼容性：只发送 user 和 assistant，避免非法 role 导致 400
                if (content != null && !content.isBlank() && ("user".equalsIgnoreCase(role) || "assistant".equalsIgnoreCase(role))) {
                    if (!content.contains("抱歉，暂时未获取到回复")) {
                        messages.add(Map.of("role", role.toLowerCase(), "content", content));
                    }
                }
            }
        }

        // 确保最后一条是用户消息
        boolean lastIsUser = !messages.isEmpty() && "user".equals(messages.get(messages.size() - 1).get("role"));
        if (!lastIsUser) {
            String userMsg = request.getMessage() != null ? request.getMessage() : "你好";
            messages.add(Map.of("role", "user", "content", userMsg));
        }

        Map<String, Object> body = new java.util.HashMap<>();
        body.put("model", aiConfig.getModel());
        body.put("messages", messages);
        body.put("stream", true);

        log.info("Request Body: {}", body);

        return webClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(String.class)
                .doOnNext(raw -> log.info("Raw Input from ModelScope: [{}]", raw))
                .flatMapIterable(rawLine -> {
                    if (rawLine == null || rawLine.isBlank()) return java.util.Collections.emptyList();

                    List<String> results = new java.util.ArrayList<>();

                    try {
                        String payload = rawLine.trim();
                        if (payload.startsWith("data:")) {
                            payload = payload.substring(5).trim();
                        }
                        if ("[DONE]".equalsIgnoreCase(payload) || "[[DONE]]".equalsIgnoreCase(payload)) {
                            return java.util.Collections.emptyList();
                        }

                        com.fasterxml.jackson.databind.JsonNode root = objectMapper.readTree(payload);
                        List<Map<String, Object>> chunkList = new java.util.ArrayList<>();
                        if (root.isArray()) {
                            for (com.fasterxml.jackson.databind.JsonNode node : root) {
                                chunkList.add(objectMapper.convertValue(node, Map.class));
                            }
                        } else if (root.isObject()) {
                            chunkList.add(objectMapper.convertValue(root, Map.class));
                        } else {
                            return java.util.Collections.emptyList();
                        }

                        for (Map<String, Object> res : chunkList) {
                            @SuppressWarnings("unchecked")
                            List<Map<String, Object>> choices = (List<Map<String, Object>>) res.get("choices");
                            if (choices != null && !choices.isEmpty()) {
                                @SuppressWarnings("unchecked")
                                Map<String, Object> delta = (Map<String, Object>) choices.get(0).get("delta");
                                if (delta != null) {
                                    String reasoning = (String) delta.get("reasoning_content");
                                    String content = (String) delta.get("content");
                                    
                                    Map<String, String> outputMap = new java.util.HashMap<>();
                                    if (reasoning != null && !reasoning.isEmpty()) outputMap.put("reasoning", reasoning);
                                    if (content != null && !content.isEmpty()) outputMap.put("text", content);
                                    
                                    if (!outputMap.isEmpty()) {
                                        String jsonResult = objectMapper.writeValueAsString(outputMap);
                                        results.add("data: " + jsonResult + "\n\n");
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.warn("Failed to parse chunk, raw=[{}], error={}", rawLine, e.getMessage());
                    }
                    return results;
                })
                .doOnNext(out -> log.info("Forwarding to Frontend: {}", out))
                .doOnSubscribe(s -> log.info("AI Stream started"))
                .doOnComplete(() -> log.info("AI Stream completed"))
                .onErrorResume(e -> {
                    log.error("AI Error: ", e);
                    String msg = e.getMessage().contains("401") ? "API Key 校验失败" : "AI 服务连接中断";
                    try {
                        return Flux.just("data: " + objectMapper.writeValueAsString(Map.of("text", "[系统提示: " + msg + "]")) + "\n\n");
                    } catch (Exception ex) {
                        return Flux.empty();
                    }
                });
    }
}
