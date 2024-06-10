package com.gayan.task_manager.controller;

import com.gayan.task_manager.model.Task;
import com.gayan.task_manager.model.TaskDTO;
import com.gayan.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/task")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO task, Principal principal) {
        try {
            taskService.addTask(task, principal.getName());
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/all")
    public List<TaskDTO> getTasks(Principal principal) {
        return taskService.getAllTasks(principal.getName());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id, Principal principal) {
        try {
            return ResponseEntity.ok(taskService.getTaskById(id, principal.getName()));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/update")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO task, Principal principal) {
        try {
            taskService.updateTask(task, principal.getName());
            return new ResponseEntity<>(taskService.getTaskById(task.id(), principal.getName()), HttpStatus.ACCEPTED);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteTask(@PathVariable Long id, Principal principal) {
        try {
            taskService.deleteTaskById(id, principal.getName());
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return HttpStatus.UNAUTHORIZED;
    }
}
