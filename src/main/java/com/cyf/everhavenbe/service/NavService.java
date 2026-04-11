package com.cyf.everhavenbe.service;

import com.cyf.everhavenbe.model.entity.NavCategory;
import com.cyf.everhavenbe.model.entity.NavItem;
import com.cyf.everhavenbe.model.vo.NavCategoryVO;
import java.util.List;

public interface NavService {
    List<NavCategoryVO> getNavTree();

    void addCategory(NavCategory category);
    void updateCategory(NavCategory category);
    void deleteCategory(Long id);

    void addItem(NavItem item);
    void updateItem(NavItem item);
    void deleteItem(Long id);
}

