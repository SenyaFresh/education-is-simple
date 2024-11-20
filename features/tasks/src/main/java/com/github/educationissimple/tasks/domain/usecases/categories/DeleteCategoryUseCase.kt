package com.github.educationissimple.tasks.domain.usecases.categories

import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

/**
 * Use case for deleting a category from the tasks repository.
 */
class DeleteCategoryUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    /**
     * Deletes a task category by its ID.
     * @param categoryId The ID of the category to delete.
     */
    suspend fun deleteCategory(categoryId: Long) {
        tasksRepository.deleteCategory(categoryId)
    }

}