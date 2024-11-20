package com.github.educationissimple.tasks.domain.usecases.categories

import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

/**
 * Use case for adding a new category to the tasks repository.
 */
class AddCategoryUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    /**
     * Creates a new task category.
     * @param name The name of the new category.
     */
    suspend fun addCategory(name: String) {
        tasksRepository.createCategory(name)
    }

}