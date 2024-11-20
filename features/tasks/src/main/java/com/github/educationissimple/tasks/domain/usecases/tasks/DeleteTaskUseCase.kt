package com.github.educationissimple.tasks.domain.usecases.tasks

import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

/**
 * Use case for deleting a task from the tasks repository.
 */
class DeleteTaskUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    /**
     * Deletes a task.
     * @param taskId The ID of the task to be deleted.
     */
    suspend fun deleteTask(taskId: Long) {
        tasksRepository.deleteTask(taskId)
    }

}