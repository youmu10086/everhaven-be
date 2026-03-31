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
@Tag(name = "Navigation Management", description = "Operations pertaining to navigation items")
public class NavController {

    private final NavService navService;

    public NavController(NavService navService) {
        this.navService = navService;
    }

    @GetMapping
    @Operation(summary = "Get navigation items", description = "Retrieves the navigation menu structure")
    public Result<List<NavCategoryVO>> getNavItems() {
        return Result.success(navService.getNavTree());
    }
}

