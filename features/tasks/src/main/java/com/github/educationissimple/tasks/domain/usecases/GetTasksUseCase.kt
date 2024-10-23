package com.github.educationissimple.tasks.domain.usecases

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun getPreviousTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getPreviousTasks()

    suspend fun getTodayTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getTodayTasks()

    suspend fun getFutureTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getFutureTasks()

    suspend fun getCompletedTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getCompletedTasks()

    suspend fun changeSortType(sortType: SortType?) {
        tasksRepository.changeSortType(sortType)
    }

    suspend fun changeCategory(categoryId: Long?) {
        tasksRepository.changeCategory(categoryId)
    }

}