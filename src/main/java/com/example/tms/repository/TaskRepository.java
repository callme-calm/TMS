package com.example.tms.repository;

import com.example.tms.model.Task;
import com.example.tms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    //List<Task> findByAssignedTo(Long assignedTo);
    List<Task> findByAssignedTo(User user);
}