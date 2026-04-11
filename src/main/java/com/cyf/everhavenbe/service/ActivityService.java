package com.cyf.everhavenbe.service;

import com.cyf.everhavenbe.model.entity.Activity;

import java.util.List;

public interface ActivityService {

    void createActivity(Activity activity);

    void updateActivity(Activity activity);

    void deleteActivity(Long id);

    Activity getActivityById(Long id);

    List<Activity> getAllActivities();
}