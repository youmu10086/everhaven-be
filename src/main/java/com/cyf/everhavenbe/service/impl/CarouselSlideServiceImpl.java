package com.cyf.everhavenbe.service.impl;

import com.cyf.everhavenbe.mapper.CarouselSlideMapper;
import com.cyf.everhavenbe.model.entity.CarouselSlide;
import com.cyf.everhavenbe.service.CarouselSlideService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarouselSlideServiceImpl implements CarouselSlideService {

    private final CarouselSlideMapper carouselSlideMapper;

    public CarouselSlideServiceImpl(CarouselSlideMapper carouselSlideMapper) {
        this.carouselSlideMapper = carouselSlideMapper;
    }

    @Override
    public List<CarouselSlide> listSlides() {
        return carouselSlideMapper.findAll();
    }

    @Override
    public CarouselSlide findById(Long id) {
        return carouselSlideMapper.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(CarouselSlide slide) {
        carouselSlideMapper.insert(slide);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CarouselSlide slide) {
        carouselSlideMapper.update(slide);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        carouselSlideMapper.delete(id);
    }
}
