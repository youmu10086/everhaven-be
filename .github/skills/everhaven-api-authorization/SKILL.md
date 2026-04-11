---
name: everhaven-api-authorization
description: "Ensure that WebConfig.java interceptor exclusions are updated when developing public, non-authenticated backend API endpoints to avoid 401 Unauthorized errors. Keywords: public API, Controller, no login required, WebConfig, addInterceptor, authentication bypass."
---

# API Authorization & Interceptor Configuration Skill

## Background
The Everhaven project implements a unified `LoginInterceptor` to secure the majority of API endpoints. A common error when building new APIs, particularly public endpoints (e.g., home page data, public activity lists, public articles) that allow visitor access, is forgetting to configure the interceptor whitelist, causing frontend requests to fail with a `401 Unauthorized`.

## Mandatory Checklist

1. **Permission Check**: Before writing any new `Controller` method, explicitly determine if it requires a valid JWT Token.
2. **Exclusion Configuration**: If the endpoint allows guest access (e.g., `GET /activities**`), you MUST open `src/main/java/com/cyf/everhavenbe/config/WebConfig.java` and append the URI path suffixes into the `.excludePathPatterns(...)` list.

## Examples

**ANTI-PATTERN**: Writing the Controller without updating WebConfig, resulting in immediate 401 errors.

**BEST PRACTICE**:
```java
// In WebConfig.java, append paths to the exclusion list
@Override
public void addInterceptors(@NonNull InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/user/login", 
                "/user/register", 
                "/activities", 
                "/activities/**"
                // ✨ Add your new unauthenticated API paths here
            );
}
```

## Common Omissions
- **Path Matching Rules**: Be sure to configure both `/{path}` and `/{path}/**` to cover list queries, parameter routes (like `/id`), and sub-routes completely.
- **Swagger Documentation**: Ensure Swagger documentation paths (`/v3/api-docs**, /swagger-ui**`) form part of the whitelist so public interface viewing works seamlessly.