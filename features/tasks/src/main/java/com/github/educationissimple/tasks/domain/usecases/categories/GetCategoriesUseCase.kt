package com.github.educationissimple.tasks.domain.usecases.categories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting categories from the tasks repository.
 */
class GetCategoriesUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    /**
     * Fetches all available task categories.
     * @return A [Flow] emitting a [ResultContainer] containing a list of task categories.
     */
    suspend fun getCategories(): Flow<ResultContainer<List<TaskCategory>>> {
        return tasksRepository.getCategories()
    }

    /**
     * Reloads all task categories from the data source.
     */
    suspend fun reloadCategories() {
        tasksRepository.reloadCategories()
    }

    /**
     * Fetches the currently selected category ID.
     * @return A [Flow] emitting a [ResultContainer] with the selected category ID.
     */
    suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>> {
        return tasksRepository.getSelectedCategoryId()
    }

}