package com.github.educationissimple.tasks.domain.usecases

import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

class ChangeCategoryUseCase@Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun changeCategory(categoryId: Long?) {
        tasksRepository.changeCategory(categoryId)
    }

}