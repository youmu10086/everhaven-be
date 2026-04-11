package com.cyf.everhavenbe.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyf.everhavenbe.common.Result;
import com.cyf.everhavenbe.model.entity.Apartment;
import com.cyf.everhavenbe.model.entity.Label;
import com.cyf.everhavenbe.model.vo.ApartmentVO;
import com.cyf.everhavenbe.service.ApartmentService;
import com.cyf.everhavenbe.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apartments")
@Tag(name = "房源管理", description = "房源信息的查询与维护")
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private LabelService labelService;

    @GetMapping("/list")
    @Operation(summary = "获取房源列表(分页)", description = "支持按分组和类型筛选房源，并返回关联标签")
    public Result<Page<ApartmentVO>> list(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(required = false) String group,
            @RequestParam(required = false) String type) {

        Page<Apartment> pageParam = new Page<>(current, size);
        LambdaQueryWrapper<Apartment> queryWrapper = new LambdaQueryWrapper<>();
        if (group != null && !group.trim().isEmpty()) {
            queryWrapper.eq(Apartment::getCategoryGroup, group);
        }
        if (type != null && !type.trim().isEmpty()) {
            queryWrapper.eq(Apartment::getCategoryType, type);
        }
        queryWrapper.orderByDesc(Apartment::getCreateTime);
        
        Page<Apartment> pageResult = apartmentService.page(pageParam, queryWrapper);

        // 转换为含有标签的 VO
        List<ApartmentVO> vos = pageResult.getRecords().stream().map(apt -> {
            List<Label> labels = labelService.getByApartmentId(apt.getId());
            return ApartmentVO.fromEntity(apt, labels);
        }).collect(Collectors.toList());

        Page<ApartmentVO> resultPage = new Page<>(current, size, pageResult.getTotal());
        resultPage.setRecords(vos);
        
        return Result.success(resultPage);
    }

    @PostMapping("/save")
    @Operation(summary = "新增或更新房源")
    public Result<String> save(@RequestBody ApartmentVO apartmentVO) {
        Apartment apartment = apartmentVO; // ApartmentVO 继承自 Apartment
        if (apartment.getId() == null) {
            apartment.setCreateTime(LocalDateTime.now());
            apartment.setUpdateTime(LocalDateTime.now());
            apartmentService.save(apartment);
        } else {
            apartment.setUpdateTime(LocalDateTime.now());
            apartmentService.updateById(apartment);
        }
        
        // 处理标签关联逻辑
        if (apartmentVO.getLabels() != null) {
            List<Long> labelIds = apartmentVO.getLabels().stream()
                    .map(Label::getId)
                    .collect(Collectors.toList());
            labelService.updateApartmentLabels(apartment.getId(), labelIds);
        }
        
        return Result.success("操作成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除房源")
    public Result<String> delete(@PathVariable Long id) {
        apartmentService.removeById(id);
        // 清理关联
        labelService.updateApartmentLabels(id, null);
        return Result.success("删除成功");
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取房源详情", description = "返回包含标签信息的房源详情")
    public Result<ApartmentVO> getById(@PathVariable Long id) {
        Apartment apt = apartmentService.getById(id);
        if (apt == null) return Result.error("房源不存在");
        List<Label> labels = labelService.getByApartmentId(id);
        return Result.success(ApartmentVO.fromEntity(apt, labels));
    }
}
