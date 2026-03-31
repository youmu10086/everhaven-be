package com.cyf.everhavenbe.service.impl;

import com.cyf.everhavenbe.config.ImageStorageProperties;
import com.cyf.everhavenbe.service.ImageStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    private static final Logger log = LoggerFactory.getLogger(ImageStorageServiceImpl.class);

    private final ImageStorageProperties properties;
    private final Map<String, String> urlCache = new ConcurrentHashMap<>();

    public ImageStorageServiceImpl(ImageStorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public String localize(String sourceUrl, String folder) {
        if (!StringUtils.hasText(sourceUrl)) {
            return sourceUrl;
        }

        String trimmed = sourceUrl.trim();
        if (isLocalUrl(trimmed)) {
            return trimmed;
        }

        String normalizedFolder = sanitizeFolder(folder);
        String cacheKey = normalizedFolder + "::" + trimmed;
        return urlCache.computeIfAbsent(cacheKey, key -> downloadAndBuildPublicUrl(trimmed, normalizedFolder));
    }

    private String downloadAndBuildPublicUrl(String sourceUrl, String folder) {
        String hash = sha256Hex(sourceUrl);
        Path rootDir = resolveRootDir();
        Path targetDir = rootDir.resolve(folder);

        try {
            Files.createDirectories(targetDir);

            Path existing = findExistingFileByHash(targetDir, hash);
            if (existing != null) {
                return buildPublicUrl(folder, existing.getFileName().toString());
            }

            HttpURLConnection connection = openConnection(sourceUrl);
            int responseCode = connection.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                log.warn("Image download failed, status={}, url={}", responseCode, sourceUrl);
                return sourceUrl;
            }

            String extension = guessExtension(connection.getContentType(), sourceUrl);
            Path tempFile = Files.createTempFile(targetDir, hash, ".tmp");
            Path targetFile = targetDir.resolve(hash + extension);

            try (InputStream inputStream = connection.getInputStream()) {
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                Files.move(tempFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            } finally {
                Files.deleteIfExists(tempFile);
                connection.disconnect();
            }

            return buildPublicUrl(folder, targetFile.getFileName().toString());
        } catch (Exception ex) {
            log.warn("Image localization failed, url={}", sourceUrl, ex);
            return sourceUrl;
        }
    }

    private Path resolveRootDir() {
        return Paths.get(properties.getLocalDir()).toAbsolutePath().normalize();
    }

    private HttpURLConnection openConnection(String sourceUrl) throws IOException {
        URL url = URI.create(sourceUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(15000);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("User-Agent", "everhaven-be-image-sync/1.0");
        return connection;
    }

    private Path findExistingFileByHash(Path targetDir, String hash) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(targetDir, hash + ".*")) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    return path;
                }
            }
        }
        return null;
    }

    private String buildPublicUrl(String folder, String fileName) {
        String prefix = normalizePublicPrefix(properties.getPublicPrefix());
        return prefix + "/" + folder + "/" + fileName;
    }

    private String normalizePublicPrefix(String prefix) {
        String normalized = StringUtils.hasText(prefix) ? prefix.trim() : "/media";
        if (!normalized.startsWith("/")) {
            normalized = "/" + normalized;
        }
        if (normalized.endsWith("/")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        return normalized;
    }

    private boolean isLocalUrl(String url) {
        String prefix = normalizePublicPrefix(properties.getPublicPrefix());
        return url.startsWith(prefix + "/") || url.startsWith("/");
    }

    private String sanitizeFolder(String folder) {
        if (!StringUtils.hasText(folder)) {
            return "misc";
        }
        return folder.replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    private String guessExtension(String contentType, String sourceUrl) {
        if (contentType != null) {
            String normalizedType = contentType.toLowerCase(Locale.ROOT);
            if (normalizedType.contains("image/jpeg") || normalizedType.contains("image/jpg")) {
                return ".jpg";
            }
            if (normalizedType.contains("image/png")) {
                return ".png";
            }
            if (normalizedType.contains("image/webp")) {
                return ".webp";
            }
            if (normalizedType.contains("image/gif")) {
                return ".gif";
            }
            if (normalizedType.contains("image/svg")) {
                return ".svg";
            }
        }

        String path = URI.create(sourceUrl).getPath();
        int dotIdx = path.lastIndexOf('.');
        if (dotIdx > -1 && dotIdx < path.length() - 1) {
            String suffix = path.substring(dotIdx).toLowerCase(Locale.ROOT);
            if (suffix.matches("\\.(jpg|jpeg|png|webp|gif|svg)$")) {
                return ".jpeg".equals(suffix) ? ".jpg" : suffix;
            }
        }
        return ".jpg";
    }

    private String sha256Hex(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(value.getBytes());
            StringBuilder sb = new StringBuilder(hashed.length * 2);
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 unavailable", ex);
        }
    }
}

