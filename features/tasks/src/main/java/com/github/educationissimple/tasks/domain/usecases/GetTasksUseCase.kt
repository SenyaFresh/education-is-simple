package com.github.educationissimple.tasks.domain.usecases

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun changeSelectionDate(date: LocalDate) {
        tasksRepository.changeSelectionDate(date)
    }

    suspend fun getNotCompletedTasksForDate(): Flow<ResultContainer<List<Task>>> {
        return tasksRepository.getNotCompletedTasksForDate()
    }

    suspend fun getCompletedTasksForDate(): Flow<ResultContainer<List<Task>>> {
        return tasksRepository.getCompletedTasksForDate()
    }

    suspend fun getPreviousTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getPreviousTasks()

    suspend fun getTodayTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getTodayTasks()

    suspend fun getFutureTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getFutureTasks()

    suspend fun getCompletedTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getCompletedTasks()

    suspend fun changeTaskSearchText(text: String) {
        tasksRepository.changeTaskSearchText(text)
    }

    suspend fun changeSortType(sortType: SortType?) {
        tasksRepository.changeSortType(sortType)
    }

    suspend fun changeCategory(categoryId: Long?) {
        tasksRepository.changeCategory(categoryId)
    }

    suspend fun getSelectedSortType(): Flow<ResultContainer<SortType?>> {
        return tasksRepository.getSelectedSortType()
    }

}