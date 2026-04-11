package com.cyf.everhavenbe.service.impl;

import com.cyf.everhavenbe.mapper.ActivityMapper;
import com.cyf.everhavenbe.model.entity.Activity;
import com.cyf.everhavenbe.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    @Override
    public void createActivity(Activity activity) {
        activityMapper.insert(activity);
    }

    @Override
    public void updateActivity(Activity activity) {
        activityMapper.update(activity);
    }

    @Override
    public void deleteActivity(Long id) {
        activityMapper.delete(id);
    }

    @Override
    public Activity getActivityById(Long id) {
        return activityMapper.findById(id);
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityMapper.findAll();
    }
}