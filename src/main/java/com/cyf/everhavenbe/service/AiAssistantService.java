package com.cyf.everhavenbe.service;

import reactor.core.publisher.Flux;

public interface AiAssistantService {
    /**
     * Streams chat responses from the AI model
     * @param request AI chat request containing messages and context
     * @return A stream of JSON strings containing the generated text
     */
    Flux<String> streamChat(com.cyf.everhavenbe.model.dto.AiChatRequestDTO request);
}
