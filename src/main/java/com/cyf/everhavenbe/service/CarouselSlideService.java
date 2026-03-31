package com.cyf.everhavenbe.service;

import com.cyf.everhavenbe.model.entity.CarouselSlide;

import java.util.List;

public interface CarouselSlideService {

    List<CarouselSlide> listSlides();

    CarouselSlide findById(Long id);

    void add(CarouselSlide slide);

    void update(CarouselSlide slide);

    void delete(Long id);
}
