package com.github.educationissimple.tasks.domain.usecases

import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository

class AddTaskUseCase(private val tasksRepository: TasksRepository) {

    suspend fun addTask(task: Task) {
        tasksRepository.addTask(task)
    }

}