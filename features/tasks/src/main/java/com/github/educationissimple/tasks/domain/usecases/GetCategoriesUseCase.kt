package com.github.educationissimple.tasks.domain.usecases

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun getCategories(): Flow<ResultContainer<List<TaskCategory>>> {
        return tasksRepository.getCategories()
    }

}