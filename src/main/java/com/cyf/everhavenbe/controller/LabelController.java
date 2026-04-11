package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.common.Result;
import com.cyf.everhavenbe.model.entity.Label;
import com.cyf.everhavenbe.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签管理控制层
 */
@RestController
@RequestMapping("/labels")
@Tag(name = "标签管理", description = "房源标签的CRUD接口")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/list")
    @Operation(summary = "获取所有标签", description = "获取标签库中的所有已定义标签")
    public Result<List<Label>> list() {
        return Result.success(labelService.list());
    }

    @PostMapping("/add")
    @Operation(summary = "添加新标签")
    public Result<Boolean> add(@RequestBody Label label) {
        return Result.success(labelService.save(label));
    }

    @PutMapping("/update")
    @Operation(summary = "更新标签信息")
    public Result<Boolean> update(@RequestBody Label label) {
        return Result.success(labelService.updateById(label));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除标签")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(labelService.removeById(id));
    }
}
