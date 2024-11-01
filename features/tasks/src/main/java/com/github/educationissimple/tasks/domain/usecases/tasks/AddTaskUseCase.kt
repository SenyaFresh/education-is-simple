package com.github.educationissimple.tasks.domain.usecases.tasks

import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun addTask(task: Task) {
        tasksRepository.addTask(task)
    }

}