package com.cyf.everhavenbe.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.image")
public class ImageStorageProperties {

    /**
     * Local directory used to persist downloaded images.
     */
    private String localDir = "uploads/images";

    /**
     * Public URL prefix used by frontend to access local images.
     */
    private String publicPrefix = "/media";

    /**
     * Optional absolute base URL, e.g. http://localhost:8080.
     * When set, API returns absolute media URLs for frontend apps on another origin.
     */
    private String publicBaseUrl;

    /**
     * Whether to synchronize images on application startup.
     */
    private boolean syncOnStartup = true;

    public String getLocalDir() {
        return localDir;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }

    public String getPublicPrefix() {
        return publicPrefix;
    }

    public void setPublicPrefix(String publicPrefix) {
        this.publicPrefix = publicPrefix;
    }

    public boolean isSyncOnStartup() {
        return syncOnStartup;
    }

    public String getPublicBaseUrl() {
        return publicBaseUrl;
    }

    public void setPublicBaseUrl(String publicBaseUrl) {
        this.publicBaseUrl = publicBaseUrl;
    }

    public void setSyncOnStartup(boolean syncOnStartup) {
        this.syncOnStartup = syncOnStartup;
    }
}
