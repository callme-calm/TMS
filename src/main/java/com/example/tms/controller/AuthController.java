package com.example.tms.controller;

import com.example.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.tms.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody User user) {
//        userService.createUser(user);
//        return ResponseEntity.ok("User registered successfully");
//    }
//}


@Controller@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

//    @PostMapping("register")
//    public String registerUser(User user) {
//        userService.createUser(user);
//        return "redirect:/login";
//    }
    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
