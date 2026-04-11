package com.cyf.everhavenbe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.everhavenbe.mapper.ApartmentMapper;
import com.cyf.everhavenbe.model.entity.Apartment;
import com.cyf.everhavenbe.service.ApartmentService;
import org.springframework.stereotype.Service;

@Service
public class ApartmentServiceImpl extends ServiceImpl<ApartmentMapper, Apartment> implements ApartmentService {
}
