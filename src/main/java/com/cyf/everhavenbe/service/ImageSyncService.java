package com.cyf.everhavenbe.service;

import java.util.Map;

public interface ImageSyncService {

    /**
     * Download all image URLs currently stored in database.
     */
    Map<String, Integer> syncAll();
}

