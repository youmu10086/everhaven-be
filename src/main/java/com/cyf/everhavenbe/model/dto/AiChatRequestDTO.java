package com.cyf.everhavenbe.model.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class AiChatRequestDTO {
    /**
     * 当前输入
     */
    private String message;

    /**
     * 历史上下文
     */
    private List<Map<String, String>> history;

    /**
     * 项目背景信息
     */
    private String projectInfo;

    /**
     * 自定义系统提示词
     */
    private String systemPrompt;
}

