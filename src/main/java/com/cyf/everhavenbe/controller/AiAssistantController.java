package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.service.AiAssistantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/assistant")
@Tag(name = "AI AI Assistant", description = "Operations related to AI customer service streaming")
public class AiAssistantController {

    private final AiAssistantService aiAssistantService;

    public AiAssistantController(AiAssistantService aiAssistantService) {
        this.aiAssistantService = aiAssistantService;
    }

    /**
     * AI Streaming chat endpoint using Server-Sent Events (SSE).
     * @param message User's input message
     * @return Flux of JSON chunks formatted as {"text": "..."}
     */
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Streaming AI chat", description = "Receives a message and returns a stream of AI-generated responses")
    public Flux<String> chatStream(@RequestBody String message) {
        // Front-end support.ts seems to send the raw input as plain string
        // If it sends JSON {"message": "..."}, we would need a DTO.
        // Assuming plain string as requested in the example.
        return aiAssistantService.streamChat(message);
    }
}

