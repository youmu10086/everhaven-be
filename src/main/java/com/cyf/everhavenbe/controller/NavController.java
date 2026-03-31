package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.common.Result;
import com.cyf.everhavenbe.model.vo.NavCategoryVO;
import com.cyf.everhavenbe.service.NavService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
