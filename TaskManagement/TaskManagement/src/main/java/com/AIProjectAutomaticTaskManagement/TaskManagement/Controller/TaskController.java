package com.AIProjectAutomaticTaskManagement.TaskManagement.Controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @GetMapping
    public List<Map<String, Object>> getAllTasks() {
        // Replace with your actual task service logic
        List<Map<String, Object>> tasks = new ArrayList<>();
        
        Map<String, Object> task1 = new HashMap<>();
        task1.put("id", 1);
        task1.put("title", "Sample Task 1");
        task1.put("description", "This is a sample task");
        task1.put("status", "PENDING");
        task1.put("createdAt", new Date());
        
        Map<String, Object> task2 = new HashMap<>();
        task2.put("id", 2);
        task2.put("title", "Sample Task 2");
        task2.put("description", "Another sample task");
        task2.put("status", "COMPLETED");
        task2.put("createdAt", new Date());
        
        tasks.add(task1);
        tasks.add(task2);
        
        return tasks;
    }
    
    @PostMapping
    public Map<String, Object> createTask(@RequestBody Map<String, Object> taskData) {
        // Add your task creation logic here
        taskData.put("id", new Random().nextInt(1000));
        taskData.put("createdAt", new Date());
        taskData.put("status", "PENDING");
        return taskData;
    }
    
    @PutMapping("/{id}")
    public Map<String, Object> updateTask(@PathVariable Long id, @RequestBody Map<String, Object> taskData) {
        // Add your task update logic here
        taskData.put("id", id);
        taskData.put("updatedAt", new Date());
        return taskData;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, String> deleteTask(@PathVariable Long id) {
        // Add your task deletion logic here
        Map<String, String> response = new HashMap<>();
        response.put("message", "Task deleted successfully");
        response.put("id", id.toString());
        return response;
    }
}
