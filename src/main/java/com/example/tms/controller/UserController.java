//package com.example.tms.controller;
//
//import com.example.tms.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import com.example.tms.model.User;
//import com.example.tms.model.Role;
//import java.util.HashSet;
//import java.util.Set;
//import org.springframework.http.ResponseEntity;
//import java.util.List;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.ui.Model;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @GetMapping("/user")
//    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
//        User user = userService.findByUsername(username);
//        return ResponseEntity.ok(user);
//    }
//
//
//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }
//
//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//    @GetMapping("/dashboard")
//    public String userDashboard() {
//        return "user_dashboard";
//    }
//
//    @GetMapping("/create")
//    public String showAddUserForm() {
//        return "register";
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<String> createUser(
//            @RequestParam String username,
//            @RequestParam String password,
//            @RequestParam Set<String> roles) {
//        try {
//            User user = new User();
//            user.setUsername(username);
//            user.setPassword(bCryptPasswordEncoder.encode(password));
//            Set<Role> roleSet = new HashSet<>();
//            for (String roleName : roles) {
//                Role role = new Role();
//                role.setName(roleName);
//                roleSet.add(role);
//            }
//            user.setRoles(roleSet);
//            userService.createUser(user);
//            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error creating user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
//        return userService.updateUser(id, userDetails);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//    }
//}

package com.example.tms.controller;

import org.springframework.ui.Model;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import com.example.tms.model.Role;
import com.example.tms.service.TaskService;
import com.example.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/dashboard")
    public String userDashboard() {
        return "user_dashboard";
    }

    @GetMapping("/create")
    public String showAddUserForm() {
        return "register";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Set<String> roles) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            Set<Role> roleSet = new HashSet<>();
            for (String roleName : roles) {
                Role role = new Role();
                role.setName(roleName);
                roleSet.add(role);
            }
            user.setRoles(roleSet);
            userService.createUser(user);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

//    @GetMapping("/task")
//    public ResponseEntity<List<Task>> getTasksByUserId(@RequestParam Long userId) {
//        List<Task> tasks = taskService.getTasksByUserId(userId);
//        return ResponseEntity.ok(tasks);
//    }

    @GetMapping("/task")
    public String showTasks(Model model) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming username is the identifier

        // Retrieve the user object (assuming you have a method to find a user by username)
        User currentUser = userService.findByUsername(username);

        // Retrieve tasks assigned to the current user
        List<Task> tasks = taskService.findTasksByAssignedTo(currentUser);

        // Add tasks to the model
        model.addAttribute("tasks", tasks);

        // Return the name of the Thymeleaf template
        return "taskView"; // This will resolve to taskView.html
    }

    @GetMapping("/tasks/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("users", userService.getAllUsers());
        return "addTask";
    }

    @PostMapping("/tasks/new")
    public String createTask(Task task) {
        taskService.createTask(task);
        return "redirect:/users/tasks";
    }
}
