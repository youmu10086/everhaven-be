package com.cyf.everhavenbe.service;

import com.cyf.everhavenbe.model.vo.NavCategoryVO;
import java.util.List;

public interface NavService {
    List<NavCategoryVO> getNavTree();
}

