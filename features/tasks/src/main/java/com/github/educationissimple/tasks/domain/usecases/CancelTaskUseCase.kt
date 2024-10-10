package com.github.educationissimple.tasks.domain.usecases

import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository

class CancelTaskUseCase(private val tasksRepository: TasksRepository) {

    suspend fun cancelTask(task: Task) {
        tasksRepository.cancelTask(task)
    }

}