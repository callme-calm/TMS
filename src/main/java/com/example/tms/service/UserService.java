package com.example.tms.service;

import com.example.tms.repository.UserRepository;
import com.example.tms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.tms.model.User;
import com.example.tms.model.Role;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;






    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(User user) {
        // Ensure roles are saved before assigning them to the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> savedRoles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role savedRole = roleRepository.findByName(role.getName());
            if (savedRole == null) {
                savedRole = roleRepository.save(role);
            }
            savedRoles.add(savedRole);
        }
        user.setRoles(savedRoles);
        logger.debug("...This is the username coming... : {}", user.getUsername());
        logger.debug("...This is the username coming... : {}", user.getRoles());
        logger.debug("...This is the username coming... : {}", user.getPassword());

        // Save user
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
//        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setRole(userDetails.getRole());
        return userRepository.save(user);


    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
    }
}
