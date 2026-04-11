package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.common.Result;
import com.cyf.everhavenbe.model.entity.Activity;
import com.cyf.everhavenbe.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public Result<Void> createActivity(@RequestBody Activity activity) {
        activityService.createActivity(activity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> updateActivity(@RequestBody Activity activity) {
        activityService.updateActivity(activity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Activity> getActivityById(@PathVariable Long id) {
        return Result.success(activityService.getActivityById(id));
    }

    @GetMapping
    public Result<List<Activity>> getAllActivities() {
        return Result.success(activityService.getAllActivities());
    }
}