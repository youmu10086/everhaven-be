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
@Tag(name = "Image Sync", description = "远程图片本地化同步操作")
public class ImageSyncController {

    private final ImageSyncService imageSyncService;

    public ImageSyncController(ImageSyncService imageSyncService) {
        this.imageSyncService = imageSyncService;
    }

    @PostMapping("/sync")
    @Operation(summary = "同步所有图片", description = "将数据库中引用的远程图片下载并存储到本地服务器")
    public Result<Map<String, Integer>> syncAllImages() {
        return Result.success(imageSyncService.syncAll());
    }
}
