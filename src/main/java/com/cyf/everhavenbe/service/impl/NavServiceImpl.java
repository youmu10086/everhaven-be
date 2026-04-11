package com.cyf.everhavenbe.service.impl;

import com.cyf.everhavenbe.mapper.NavMapper;
import com.cyf.everhavenbe.model.entity.NavCategory;
import com.cyf.everhavenbe.model.entity.NavItem;
import com.cyf.everhavenbe.model.vo.NavCategoryVO;
import com.cyf.everhavenbe.model.vo.NavItemVO;
import com.cyf.everhavenbe.service.ImageStorageService;
import com.cyf.everhavenbe.service.NavService;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
public class NavServiceImpl implements NavService {

    private final NavMapper navMapper;
    private final ImageStorageService imageStorageService;

    public NavServiceImpl(NavMapper navMapper, ImageStorageService imageStorageService) {
        this.navMapper = navMapper;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public List<NavCategoryVO> getNavTree() {
        List<NavCategory> categories = navMapper.findAllCategories();
        List<NavItem> items = navMapper.findAllItems();

        // Group items by categoryId
        Map<Long, List<NavItem>> itemsMap = items.stream()
            .collect(Collectors.groupingBy(NavItem::getCategoryId));

        return categories.stream().map(cat -> {
            NavCategoryVO vo = new NavCategoryVO();
            vo.setId(cat.getId());
            vo.setName(cat.getName());
            
            List<NavItem> catItems = itemsMap.getOrDefault(cat.getId(), Collections.emptyList());
            List<NavItemVO> itemVOs = catItems.stream()
                .map(this::convertToItemVO)
                .collect(Collectors.toList());
            
            vo.setContent(itemVOs);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void addCategory(NavCategory category) {
        navMapper.insertCategory(category);
    }

    @Override
    public void updateCategory(NavCategory category) {
        navMapper.updateCategory(category);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteCategory(Long id) {
        navMapper.deleteItemsByCategoryId(id);
        navMapper.deleteCategory(id);
    }

    @Override
    public void addItem(NavItem item) {
        navMapper.insertItem(item);
    }

    @Override
    public void updateItem(NavItem item) {
        navMapper.updateItem(item);
    }

    @Override
    public void deleteItem(Long id) {
        navMapper.deleteItem(id);
    }

    private NavItemVO convertToItemVO(NavItem item) {
        NavItemVO vo = new NavItemVO();
        BeanUtils.copyProperties(item, vo);
        vo.setImage(imageStorageService.localize(vo.getImage(), "nav"));
        return vo;
    }
}

