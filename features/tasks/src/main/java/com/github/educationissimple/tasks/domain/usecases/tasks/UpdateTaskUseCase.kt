package com.github.educationissimple.tasks.domain.usecases.tasks

import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

/**
 * Use case for updating an existing task.
 */
class UpdateTaskUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    /**
     * Updates an existing task.
     * @param task The updated task.
     */
    suspend fun updateTask(task: Task) {
        tasksRepository.updateTask(task)
    }

}