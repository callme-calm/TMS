package com.example.tms.service;

import com.example.tms.config.ResourceNotFoundException;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import com.example.tms.repository.TaskRepository;
import com.example.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;



@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);


//    public interface TaskService {
//        List<Task> findTasksByAssignedTo(User user);
//    }

//    public List<Task> getTasksByUserId(Long userId) {
//        return taskRepository.findByAssignedTo(userId);
//    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        System.out.println("Task: in the repository " + task);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = getTaskById(id);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDeadline(taskDetails.getDeadline());
        task.setPriority(taskDetails.getPriority());
        task.setCategory(taskDetails.getCategory());
        task.setStatus(taskDetails.getStatus());
        task.setAssignedTo(taskDetails.getAssignedTo());
        logger.debug("...This is the taskdetail getcategroy.. : {}", taskDetails.getCategory());
        logger.debug("...This is the task get category coming... : {}", task.getCategory());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    public List<Task> findTasksByAssignedTo(User currentUser) {
        return taskRepository.findByAssignedTo(currentUser);
    }



    // Other methods...

    public void addMessage(Long taskId, String message) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setMessage(message);
        taskRepository.save(task);
    }

    public void submitTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setSubmitted(true);
        taskRepository.save(task);
    }

    public List<Task> getSubmittedTasks() {
        return taskRepository.findBySubmitted(true);
    }
}
