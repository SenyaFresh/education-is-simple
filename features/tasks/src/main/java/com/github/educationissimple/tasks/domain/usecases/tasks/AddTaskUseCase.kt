package com.github.educationissimple.tasks.domain.usecases.tasks

import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

/**
 * Use case for adding a new task to the tasks repository.
 */
class AddTaskUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    /**
     * Adds a new task to the tasks repository.
     * @param task The task to be added.
     */
    suspend fun addTask(task: Task) {
        tasksRepository.addTask(task)
    }

}