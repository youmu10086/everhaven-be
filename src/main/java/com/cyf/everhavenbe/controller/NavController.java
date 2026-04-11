package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.common.Result;
import com.cyf.everhavenbe.model.entity.NavCategory;
import com.cyf.everhavenbe.model.entity.NavItem;
import com.cyf.everhavenbe.model.vo.NavCategoryVO;
import com.cyf.everhavenbe.service.NavService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home/nav_items")
@Tag(name = "Navigation Management", description = "导航栏菜单管理操作")
public class NavController {

    private final NavService navService;

    public NavController(NavService navService) {
        this.navService = navService;
    }

    @GetMapping
    @Operation(summary = "获取导航项", description = "获取系统首页的导航菜单树形结构")
    public Result<List<NavCategoryVO>> getNavItems() {
        return Result.success(navService.getNavTree());
    }

    @PostMapping("/categories")
    @Operation(summary = "新增分类", description = "新增一个导航分类")
    public Result<Void> addCategory(@RequestBody NavCategory category) {
        navService.addCategory(category);
        return Result.success();
    }

    @PutMapping("/categories/{id}")
    @Operation(summary = "更新分类", description = "更新导航分类名称")
    public Result<Void> updateCategory(@PathVariable Long id, @RequestBody NavCategory category) {
        category.setId(id);
        navService.updateCategory(category);
        return Result.success();
    }

    @DeleteMapping("/categories/{id}")
    @Operation(summary = "删除分类", description = "删除分类及其下所有导航项")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        navService.deleteCategory(id);
        return Result.success();
    }

    @PostMapping("/items")
    @Operation(summary = "新增导航项", description = "在指定分类下新增一个导航项")
    public Result<Void> addItem(@RequestBody NavItem item) {
        navService.addItem(item);
        return Result.success();
    }

    @PutMapping("/items/{id}")
    @Operation(summary = "更新导航项", description = "更新导航项详细信息")
    public Result<Void> updateItem(@PathVariable Long id, @RequestBody NavItem item) {
        if (item.getCategoryId() == null) {
            return Result.error("category_id 不能为空");
        }
        item.setId(id);
        navService.updateItem(item);
        return Result.success();
    }

    @DeleteMapping("/items/{id}")
    @Operation(summary = "删除导航项", description = "根据ID删除特定导航项")
    public Result<Void> deleteItem(@PathVariable Long id) {
        navService.deleteItem(id);
        return Result.success();
    }
}
