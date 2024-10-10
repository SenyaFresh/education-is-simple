package com.github.educationissimple.tasks.domain.usecases

import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository

class DeleteTaskUseCase(private val tasksRepository: TasksRepository) {

    suspend fun deleteTask(task: Task) {
        tasksRepository.deleteTask(task)
    }

}