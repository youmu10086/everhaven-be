package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.model.dto.AiChatRequestDTO;
import com.cyf.everhavenbe.service.AiAssistantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/assistant")
@Tag(name = "AI Assistant", description = "AI 客服助手相关接口")
public class AiAssistantController {

    private final AiAssistantService aiAssistantService;

    public AiAssistantController(AiAssistantService aiAssistantService) {
        this.aiAssistantService = aiAssistantService;
    }

    /**
     * AI Streaming chat endpoint using Server-Sent Events (SSE).
     * @param request User's input message wrapped in AiChatRequestDTO
     * @return Flux of JSON chunks formatted as {"text": "..."}
     */
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "流式 AI 对话", description = "接收用户消息并返回流式的 AI 生成响应")
    public Flux<String> chatStream(@RequestBody AiChatRequestDTO request) {
        return aiAssistantService.streamChat(request);
    }
}
