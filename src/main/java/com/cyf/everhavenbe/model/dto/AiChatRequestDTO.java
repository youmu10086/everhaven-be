package com.cyf.everhavenbe.model.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class AiChatRequestDTO {
    private String message;
    private List<Map<String, String>> history;
    private String projectInfo;
    private String systemPrompt;
    private List<Map<String, String>> messages;
}

