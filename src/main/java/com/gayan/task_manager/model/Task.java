package com.gayan.task_manager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Task {

    public Task(TaskDTO taskDTO) {
        this.id = taskDTO.id();
        this.title = taskDTO.title();
        this.description = taskDTO.description();
        this.status = taskDTO.status();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private User user;
    private String title;
    private String description;
    private CompStatus status;
}
