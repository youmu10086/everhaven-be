package com.cyf.everhavenbe.service.impl;

import com.cyf.everhavenbe.mapper.CarouselSlideMapper;
import com.cyf.everhavenbe.mapper.NavMapper;
import com.cyf.everhavenbe.model.entity.CarouselSlide;
import com.cyf.everhavenbe.model.entity.NavItem;
import com.cyf.everhavenbe.service.ImageStorageService;
import com.cyf.everhavenbe.service.ImageSyncService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageSyncServiceImpl implements ImageSyncService {

    private final CarouselSlideMapper carouselSlideMapper;
    private final NavMapper navMapper;
    private final ImageStorageService imageStorageService;

    public ImageSyncServiceImpl(CarouselSlideMapper carouselSlideMapper,
                                NavMapper navMapper,
                                ImageStorageService imageStorageService) {
        this.carouselSlideMapper = carouselSlideMapper;
        this.navMapper = navMapper;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public Map<String, Integer> syncAll() {
        int total = 0;
        int localized = 0;

        List<CarouselSlide> slides = carouselSlideMapper.findAll();
        for (CarouselSlide slide : slides) {
            total++;
            if (isLocalized(imageStorageService.localize(slide.getImage(), "carousel"), slide.getImage())) {
                localized++;
            }

            if (slide.getImageDark() != null && !slide.getImageDark().isBlank()) {
                total++;
                if (isLocalized(imageStorageService.localize(slide.getImageDark(), "carousel"), slide.getImageDark())) {
                    localized++;
                }
            }
        }

        List<NavItem> navItems = navMapper.findAllItems();
        for (NavItem navItem : navItems) {
            total++;
            if (isLocalized(imageStorageService.localize(navItem.getImage(), "nav"), navItem.getImage())) {
                localized++;
            }
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("total", total);
        result.put("localized", localized);
        result.put("failed", Math.max(total - localized, 0));
        return result;
    }

    private boolean isLocalized(String outputUrl, String inputUrl) {
        return outputUrl != null && !outputUrl.equals(inputUrl);
    }
}

