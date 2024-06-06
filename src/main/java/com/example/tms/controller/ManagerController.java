//package com.example.tms.controller;
//
//import com.example.tms.model.Task;
//import com.example.tms.service.TaskService;
//import com.example.tms.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import com.example.tms.model.User;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/manager")
//public class ManagerController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private TaskService taskService;
//
//    @GetMapping("/dashboard")
//    public String dashboard() {
//        return "manager_dashboard";
//    }
//
//    @GetMapping("/adduser")
//    public String showAddUserForm() {
//        return "register";
//    }
//
//    @PostMapping("/adduser")
//    public String addUser(User user) {
//        userService.createUser(user);
//        return "redirect:/manager/dashboard";
//    }
//
//    @GetMapping("/tasks")
//    public String showTasks(Model model) {
//        List<Task> tasks = taskService.getAllTasks();
//        model.addAttribute("tasks", tasks);
//        return "tasks";
//    }
//
//    @GetMapping("/tasks/new")
//    public String showTaskForm(Model model) {
//        model.addAttribute("task", new Task());
//        model.addAttribute("users", userService.getAllUsers());
//        return "addTask";
//    }
//
//    @PostMapping("/tasks/new")
//    public String createTask(Task task) {
//        taskService.createTask(task);
//        return "redirect:/manager/tasks";
//    }
//
//    @GetMapping("/tasks/edit/{id}")
//    public String showEditTaskForm(@PathVariable Long id, Model model) {
//        Task task = taskService.getTaskById(id);
//        model.addAttribute("task", task);
//        model.addAttribute("users", userService.getAllUsers());
//        return "task_form";
//    }
//
//    @PostMapping("/tasks/update/{id}")
//    public String updateTask(@PathVariable Long id, Task task) {
//        taskService.updateTask(id, task);
//        return "redirect:/manager/tasks";
//    }
//}

package com.example.tms.controller;

import com.example.tms.model.Role;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import com.example.tms.service.RoleService;
import com.example.tms.service.TaskService;
import com.example.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {


    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/dashboard")
    public String dashboard() {
        logger.debug("Accessing manager dashboard");
        return "manager_dashboard";
    }

    @GetMapping("/adduser")
    public String showAddUserForm() {
        return "register";
    }

    @PostMapping("/adduser")
    public String addUser(User user) {
        userService.createUser(user);
        return "redirect:/manager/dashboard";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

//    @GetMapping("/users/edit/{id}")
//    public String showEditUserForm(@PathVariable Long id, Model model) {
//        User user = userService.getUserById(id);
//        model.addAttribute("user", user);
//        return "edit_user";
//    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> allRoles = roleService.findAll(); // Fetch all roles

        // Log the roles to ensure they are being fetched
        allRoles.forEach(role -> System.out.println("Role: " + role.getName()));

        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles); // Add roles to model
        return "edit_user";
    }

 //   @PostMapping("/users/update/{id}")
//    public String updateUser(@PathVariable Long id, User user) {
//        userService.updateUser(id, user);
//        return "redirect:/manager/users";
//    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, @RequestParam("roles") Long roleId , RedirectAttributes redirectAttributes ) {
        User existingUser = userService.getUserById(id);
        existingUser.setUsername(user.getUsername());
//        existingUser.setPassword(user.getPassword()); // Consider encoding the password
        Role role = roleService.findById(roleId);
        existingUser.setRole(role);
        userService.save(existingUser);
       redirectAttributes.addFlashAttribute("message", "User updated successfully");
        return "redirect:/manager/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/manager/users";
    }

    @GetMapping("/tasks")
    public String showTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/tasks/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("users", userService.getAllUsers());
        return "addTask";
    }

    @PostMapping("/tasks/new")
    public String createTask(Task task) {

        System.out.println("Task: " + task);
        taskService.createTask(task);
        return "redirect:/manager/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        model.addAttribute("users", userService.getAllUsers());
        return "task_form";
    }

//    @PutMapping("/tasks/update/{id}")
    @PostMapping(value = "/tasks/update/{id}", consumes = "application/json")
    public String updateTask(@PathVariable Long id,@RequestBody Task task) {

        logger.debug("...This is the entire task ... : {}", task.getCategory());
        logger.debug("...This is the entire task ... : {}", task.getAssignedTo());
        logger.debug("...This is the entire task ... : {}", task.getDeadline());
        logger.debug("...This is the entire task ... : {}", task.getId());
        logger.debug("...This is the entire task ... : {}", task.getTitle());
        logger.debug("...This is the entire task ... : {}", task.getDescription());
        logger.debug("...This is the entire task ... : {}", task.getPriority());



        taskService.updateTask(id, task);

        return "redirect:/manager/tasks";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/manager/tasks";
    }
}
