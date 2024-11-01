package com.github.educationissimple.tasks.domain.usecases.categories

import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun deleteCategory(categoryId: Long) {
        tasksRepository.deleteCategory(categoryId)
    }

}