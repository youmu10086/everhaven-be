package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.pojo.CarouselSlide;
import com.cyf.everhavenbe.pojo.Result;
import com.cyf.everhavenbe.service.CarouselSlideService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carousel_slides")
public class CarouselSlideController {

    private final CarouselSlideService carouselSlideService;

    public CarouselSlideController(CarouselSlideService carouselSlideService) {
        this.carouselSlideService = carouselSlideService;
    }

    @GetMapping
    public Result<List<CarouselSlide>> list() {
        return Result.success(carouselSlideService.listSlides());
    }
}

