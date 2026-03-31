package com.cyf.everhavenbe.service;

import reactor.core.publisher.Flux;

public interface AiAssistantService {
    /**
     * Streams chat responses from the AI model
     * @param message User's input message
     * @return A stream of JSON strings containing the generated text
     */
    Flux<String> streamChat(String message);
}

