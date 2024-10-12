package com.github.educationissimple.tasks.domain.usecases

import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun deleteTask(task: Task) {
        tasksRepository.deleteTask(task)
    }

}