package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.model.entity.CarouselSlide;
import com.cyf.everhavenbe.common.Result;
import com.cyf.everhavenbe.service.ImageStorageService;
import com.cyf.everhavenbe.service.CarouselSlideService;
import com.cyf.everhavenbe.model.vo.CarouselSlideVO;
import com.cyf.everhavenbe.model.dto.CarouselSlideDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home/carousel_slides")
@Tag(name = "Carousel Management", description = "轮播图管理相关操作")
public class CarouselSlideController {

    // 声明私有 CarouselSlideService
    private final CarouselSlideService carouselSlideService;
    private final ImageStorageService imageStorageService;

    // 构造函数
    public CarouselSlideController(CarouselSlideService carouselSlideService,
                                   ImageStorageService imageStorageService) {
        this.carouselSlideService = carouselSlideService;
        this.imageStorageService = imageStorageService;
    }

    @GetMapping
    @Operation(summary = "获取轮播图列表", description = "获取首页所有展示的轮播图信息")
    public Result<List<CarouselSlideVO>> list() {
        List<CarouselSlide> slides = carouselSlideService.listSlides();
        // stream 流式处理，转换为 VO 列表
        // map(this::convertToVO)：对每一个 CarouselSlide实体，调用 convertToVO方法，把它转换成一个 CarouselSlideVO对象
        // collect(Collectors.toList())：把转换后的流收集成一个 List<CarouselSlideVO>对象
        List<CarouselSlideVO> voList = slides.stream().map(this::convertToVO).collect(Collectors.toList());
        return Result.success(voList);
    }

    @PostMapping
    @Operation(summary = "新增轮播图", description = "添加一张新的轮播图数据")
    public Result<Void> add(@RequestBody @Validated CarouselSlideDTO carouselSlideDTO) {
        CarouselSlide slide = new CarouselSlide();
        BeanUtils.copyProperties(carouselSlideDTO, slide);
        carouselSlideService.add(slide);
        return Result.success();
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新轮播图", description = "根据ID修改现有的轮播图信息")
    public Result<Void> update(@PathVariable Long id, @RequestBody @Validated CarouselSlideDTO carouselSlideDTO) {
        CarouselSlide slide = new CarouselSlide();
        BeanUtils.copyProperties(carouselSlideDTO, slide);
        slide.setId(id);
        carouselSlideService.update(slide);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除轮播图", description = "根据ID删除指定的轮播图")
    public Result<Void> delete(@PathVariable Long id) {
        carouselSlideService.delete(id);
        return Result.success();
    }

    private CarouselSlideVO convertToVO(CarouselSlide slide) {
        CarouselSlideVO vo = new CarouselSlideVO();
        BeanUtils.copyProperties(slide, vo);
        vo.setImage(imageStorageService.localize(vo.getImage(), "carousel"));
        vo.setImageDark(imageStorageService.localize(vo.getImageDark(), "carousel"));
        return vo;
    }
}
