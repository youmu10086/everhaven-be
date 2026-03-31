package com.cyf.everhavenbe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    private final ImageStorageProperties imageStorageProperties;

    public StaticResourceConfig(ImageStorageProperties imageStorageProperties) {
        this.imageStorageProperties = imageStorageProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String publicPrefix = normalizePublicPrefix(imageStorageProperties.getPublicPrefix());
        Path imageRoot = Paths.get(imageStorageProperties.getLocalDir()).toAbsolutePath().normalize();
        String fileLocation = imageRoot.toUri().toString();

        registry.addResourceHandler(publicPrefix + "/**")
                .addResourceLocations(fileLocation);
    }

    private String normalizePublicPrefix(String prefix) {
        if (prefix == null || prefix.isBlank()) {
            return "/media";
        }
        String normalized = prefix.trim();
        if (!normalized.startsWith("/")) {
            normalized = "/" + normalized;
        }
        if (normalized.endsWith("/")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        return normalized;
    }
}

