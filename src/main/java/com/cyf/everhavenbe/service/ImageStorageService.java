package com.cyf.everhavenbe.service;

public interface ImageStorageService {

    /**
     * Download a remote image (if needed) and return frontend-accessible URL.
     *
     * @param sourceUrl original URL from database
     * @param folder logical subfolder, such as carousel/nav
     * @return local public URL on success, or original URL when unavailable
     */
    String localize(String sourceUrl, String folder);
}

