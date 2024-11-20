package com.github.educationissimple.tasks.domain.usecases.tasks

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

/**
 * Use case for getting tasks from the tasks repository.
 */
class GetTasksUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    /**
     * Updates the currently selected date.
     * @param date The new selected date.
     */
    suspend fun changeSelectionDate(date: LocalDate) {
        tasksRepository.changeSelectionDate(date)
    }

    /**
     * Fetches tasks that are not completed for the selected date.
     * @return A [Flow] emitting a [ResultContainer] containing a list of incomplete tasks.
     */
    suspend fun getNotCompletedTasksForDate(): Flow<ResultContainer<List<Task>>> {
        return tasksRepository.getNotCompletedTasksForDate()
    }

    /**
     * Fetches tasks that are completed for the selected date.
     * @return A [Flow] emitting a [ResultContainer] containing a list of completed tasks.
     */
    suspend fun getCompletedTasksForDate(): Flow<ResultContainer<List<Task>>> {
        return tasksRepository.getCompletedTasksForDate()
    }

    /**
     * Fetches tasks with deadlines before the current date.
     * @return A [Flow] emitting a [ResultContainer] containing a list of previous tasks.
     */
    suspend fun getPreviousTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getPreviousTasks()

    /**
     * Fetches tasks with deadlines today.
     * @return A [Flow] emitting a [ResultContainer] containing a list of today's tasks.
     */
    suspend fun getTodayTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getTodayTasks()

    /**
     * Fetches tasks with deadlines in the future.
     * @return A [Flow] emitting a [ResultContainer] containing a list of future tasks.
     */
    suspend fun getFutureTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getFutureTasks()

    /**
     * Fetches tasks that are completed.
     * @return A [Flow] emitting a [ResultContainer] containing a list of completed tasks.
     */
    suspend fun getCompletedTasks(): Flow<ResultContainer<List<Task>>> =
        tasksRepository.getCompletedTasks()

    /**
     * Updates the search text used for filtering tasks.
     * @param text The new search text.
     */
    suspend fun changeTaskSearchText(text: String) {
        tasksRepository.changeTaskSearchText(text)
    }

    /**
     * Changes the sorting type for tasks.
     * @param sortType The new sorting type or `null` to clear sorting.
     */
    suspend fun changeSortType(sortType: SortType?) {
        tasksRepository.changeSortType(sortType)
    }

    /**
     * Changes the selected category by its ID.
     * @param categoryId The new category ID or `null` to clear the selection.
     */
    suspend fun changeCategory(categoryId: Long?) {
        tasksRepository.changeCategory(categoryId)
    }

    /**
     * Fetches the currently selected sorting type.
     * @return A flow emitting a [ResultContainer] with the selected [SortType].
     */
    suspend fun getSelectedSortType(): Flow<ResultContainer<SortType?>> {
        return tasksRepository.getSelectedSortType()
    }

    /**
     * Reloads all tasks from the data source.
     */
    suspend fun reloadTasks() {
        tasksRepository.reloadTasks()
    }
}