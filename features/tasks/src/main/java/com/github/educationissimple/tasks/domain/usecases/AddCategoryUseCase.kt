package com.github.educationissimple.tasks.domain.usecases

import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun addCategory(name: String) {
        tasksRepository.createCategory(name)
    }

}