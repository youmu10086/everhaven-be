package com.cyf.everhavenbe.model.vo;

import lombok.Data;
import java.util.List;

@Data
public class NavCategoryVO {
    private Long id;
    private String name;
    private List<NavItemVO> content;
}

