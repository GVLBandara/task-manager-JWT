package com.gayan.task_manager.model;

public record TaskDTO(
        Long id,
        String title,
        String description,
        CompStatus status
) {
}
