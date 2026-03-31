package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.common.Result;
import com.cyf.everhavenbe.service.ImageSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/home/assets")
@Tag(name = "Image Sync", description = "Download remote images to local storage")
public class ImageSyncController {

    private final ImageSyncService imageSyncService;

    public ImageSyncController(ImageSyncService imageSyncService) {
        this.imageSyncService = imageSyncService;
    }

    @PostMapping("/sync")
    @Operation(summary = "Sync image URLs", description = "Downloads all image URLs from DB into local storage")
    public Result<Map<String, Integer>> syncAllImages() {
        return Result.success(imageSyncService.syncAll());
    }
}

