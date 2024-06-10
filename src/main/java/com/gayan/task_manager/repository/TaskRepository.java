package com.gayan.task_manager.repository;

import com.gayan.task_manager.model.Task;
import com.gayan.task_manager.model.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends ListCrudRepository<Task, Long> {
    List<Task> findAllByUser(User user);
    Optional<Task> findByIdAndUser(Long id, User user);
}
