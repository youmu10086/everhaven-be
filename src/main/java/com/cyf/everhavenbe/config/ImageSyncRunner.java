package com.cyf.everhavenbe.config;

import com.cyf.everhavenbe.service.ImageSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ImageSyncRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ImageSyncRunner.class);

    private final ImageStorageProperties properties;
    private final ImageSyncService imageSyncService;

    public ImageSyncRunner(ImageStorageProperties properties, ImageSyncService imageSyncService) {
        this.properties = properties;
        this.imageSyncService = imageSyncService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!properties.isSyncOnStartup()) {
            return;
        }

        Map<String, Integer> result = imageSyncService.syncAll();
        log.info("Image sync completed: {}", result);
    }
}

