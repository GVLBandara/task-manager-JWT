package com.gayan.task_manager.service;

import com.gayan.task_manager.model.Task;
import com.gayan.task_manager.model.TaskDTO;
import com.gayan.task_manager.model.User;
import com.gayan.task_manager.repository.TaskRepository;
import com.gayan.task_manager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public void addTask(TaskDTO taskDTO, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        Task task = new Task(taskDTO);
        if(user.isPresent()){
            task.setUser(user.get());
            taskRepository.save(task);
        }
    }

    public List<TaskDTO> getAllTasks(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return taskRepository.findAllByUser(user.get()).stream().map(this::toTaskDTO).toList();
    }

    public TaskDTO getTaskById(Long id, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return taskRepository.findByIdAndUser(id, user.get()).map(this::toTaskDTO).orElseThrow(EntityNotFoundException::new);
    }

    public void updateTask(TaskDTO taskDTO, String username) {
        User user = userRepository.findByUsername(username).get();
        if(taskRepository.findByIdAndUser(taskDTO.id(), user).isPresent()){
            Task task = new Task(taskDTO);
            task.setUser(user);
            taskRepository.save(task);
        }else throw new EntityNotFoundException("Task not found");
    }

    public void deleteTaskById(Long id, String username) {
        Optional<Task> task = taskRepository.findByIdAndUser(id, userRepository.findByUsername(username).get());
        if(task.isPresent()){
            taskRepository.delete(task.get());
        }
        else throw new EntityNotFoundException("Task not found");
    }

    private TaskDTO toTaskDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus());
    }
}
