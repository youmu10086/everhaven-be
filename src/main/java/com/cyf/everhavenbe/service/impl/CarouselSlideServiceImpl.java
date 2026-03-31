package com.cyf.everhavenbe.service.impl;

import com.cyf.everhavenbe.mapper.CarouselSlideMapper;
import com.cyf.everhavenbe.pojo.CarouselSlide;
import com.cyf.everhavenbe.service.CarouselSlideService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselSlideServiceImpl implements CarouselSlideService {

    private final CarouselSlideMapper carouselSlideMapper;

    public CarouselSlideServiceImpl(CarouselSlideMapper carouselSlideMapper) {
        this.carouselSlideMapper = carouselSlideMapper;
    }

    @Override
    public List<CarouselSlide> listSlides() {
        return carouselSlideMapper.findAll();
    }
}

