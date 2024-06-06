package com.example.tms.controller;

import com.example.tms.model.Task;
import com.example.tms.model.TaskDTO;
import com.example.tms.service.TaskService;
import com.example.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tms.model.User;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/task_form")
    public String showTaskForm(Model model) {
        // Add any necessary model attributes here
        return "task_form"; // Assuming your HTML file is named task-form.html
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
//        User user = userService.findByUsername(username);
//        task.setAssignedTo(user);
        System.out.println("Received JSON payload: " + task);
        return taskService.createTask(task);
    }

//    @PostMapping
//    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
//        Task task = new Task();
//        task.setTitle(taskDTO.getTitle());
//        task.setDescription(taskDTO.getDescription());
//        task.setDeadline(LocalDateTime.parse(taskDTO.getDeadline(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//        task.setPriority(Task.Priority.valueOf(taskDTO.getPriority()));
//        task.setCategory(Task.Category.valueOf(taskDTO.getCategory()));
//        task.setStatus(Task.Status.valueOf(taskDTO.getStatus()));
//
//        User user = new User();
//        user.setId(taskDTO.getAssignedTo());
//        task.setAssignedTo(user);
//
//        Task createdTask =  taskService.createTask(task);
//        return ResponseEntity.ok(createdTask);
//    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
