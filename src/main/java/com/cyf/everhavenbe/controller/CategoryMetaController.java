package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.common.Result;
import com.cyf.everhavenbe.model.entity.CategoryMeta;
import com.cyf.everhavenbe.repository.CategoryMetaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category_meta")
@Tag(name = "分类落地页管理接口")
public class CategoryMetaController {

    @Autowired
    private CategoryMetaRepository categoryMetaRepository;

    @GetMapping("/list")
    @Operation(summary = "获取所有分类落地页配置")
    public Result<List<CategoryMeta>> list() {
        return Result.success(categoryMetaRepository.findAll());
    }

    @GetMapping("/{group}/{type}")
    @Operation(summary = "根据分类获取单一前台落地页配置")
    public Result<CategoryMeta> getByGroupAndType(@PathVariable String group, @PathVariable String type) {
        return Result.success(categoryMetaRepository.findByCategoryGroupAndCategoryType(group, type));
    }

    @PostMapping("/save")
    @Operation(summary = "新增或更新分类配置")
    public Result<String> save(@RequestBody CategoryMeta categoryMeta) {
        // 防止重复保存同组同类
        if (categoryMeta.getId() == null) {
            CategoryMeta existing = categoryMetaRepository.findByCategoryGroupAndCategoryType(categoryMeta.getCategoryGroup(), categoryMeta.getCategoryType());
            if (existing != null) {
                categoryMeta.setId(existing.getId());
            }
        }
        categoryMetaRepository.save(categoryMeta);
        return Result.success("保存成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除配置")
    public Result<String> delete(@PathVariable Long id) {
        categoryMetaRepository.deleteById(id);
        return Result.success("删除成功");
    }
}