package com.github.educationissimple.tasks.domain.repositories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Interface for managing tasks, categories, and reminders, encapsulating all necessary CRUD operations
 * and data flows for task-related functionalities.
 */
interface TasksRepository {

    /**
     * Updates the currently selected date.
     * @param date The new selected date.
     */
    suspend fun changeSelectionDate(date: LocalDate)

    /**
     * Fetches tasks that are not completed for the selected date.
     * @return A [Flow] emitting a [ResultContainer] containing a list of incomplete tasks.
     */
    suspend fun getNotCompletedTasksForDate() : Flow<ResultContainer<List<Task>>>

    /**
     * Fetches tasks that are completed for the selected date.
     * @return A [Flow] emitting a [ResultContainer] containing a list of completed tasks.
     */
    suspend fun getCompletedTasksForDate() : Flow<ResultContainer<List<Task>>>

    /**
     * Fetches tasks with deadlines before the current date.
     * @return A [Flow] emitting a [ResultContainer] containing a list of previous tasks.
     */
    suspend fun getPreviousTasks() : Flow<ResultContainer<List<Task>>>

    /**
     * Fetches tasks with deadlines today.
     * @return A [Flow] emitting a [ResultContainer] containing a list of today's tasks.
     */
    suspend fun getTodayTasks() : Flow<ResultContainer<List<Task>>>

    /**
     * Fetches tasks with deadlines in the future.
     * @return A [Flow] emitting a [ResultContainer] containing a list of future tasks.
     */
    suspend fun getFutureTasks() : Flow<ResultContainer<List<Task>>>

    /**
     * Fetches tasks that are completed.
     * @return A [Flow] emitting a [ResultContainer] containing a list of completed tasks.
     */
    suspend fun getCompletedTasks() : Flow<ResultContainer<List<Task>>>

    /**
     * Reloads all tasks from the data source.
     */
    suspend fun reloadTasks()

    /**
     * Updates the search text used for filtering tasks.
     * @param text The new search text.
     */
    suspend fun changeTaskSearchText(text: String)

    /**
     * Fetches the currently selected sorting type.
     * @return A flow emitting a [ResultContainer] with the selected [SortType].
     */
    suspend fun getSelectedSortType() : Flow<ResultContainer<SortType?>>

    /**
     * Changes the sorting type for tasks.
     * @param sortType The new sorting type or `null` to clear sorting.
     */
    suspend fun changeSortType(sortType: SortType?)

    /**
     * Adds a new task.
     * @param task The task to be added.
     */
    suspend fun addTask(task: Task)

    /**
     * Updates an existing task.
     * @param task The updated task.
     */
    suspend fun updateTask(task: Task)

    /**
     * Deletes a task.
     * @param taskId The ID of the task to be deleted.
     */
    suspend fun deleteTask(taskId: Long)

    /**
     * Fetches all available task categories.
     * @return A [Flow] emitting a [ResultContainer] containing a list of task categories.
     */
    suspend fun getCategories(): Flow<ResultContainer<List<TaskCategory>>>

    /**
     * Reloads all task categories from the data source.
     */
    suspend fun reloadCategories()

    /**
     * Fetches the currently selected category ID.
     * @return A [Flow] emitting a [ResultContainer] with the selected category ID.
     */
    suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>>

    /**
     * Creates a new task category.
     * @param name The name of the new category.
     */
    suspend fun createCategory(name: String)

    /**
     * Deletes a task category by its ID.
     * @param categoryId The ID of the category to delete.
     */
    suspend fun deleteCategory(categoryId: Long)

    /**
     * Changes the selected category by its ID.
     * @param categoryId The new category ID or `null` to clear the selection.
     */
    suspend fun changeCategory(categoryId: Long?)

    /**
     * Fetches all reminders.
     * @return A [Flow] emitting a [ResultContainer] containing a list of all task reminders.
     */
    suspend fun getAllReminders(): Flow<ResultContainer<List<TaskReminder>>>

    /**
     * Reloads all reminders from the data source.
     */
    suspend fun reloadReminders()


    /**
     * Fetches all reminders associated with a specific task.
     * @param taskId The ID of the task whose reminders are to be fetched.
     * @return A [Flow] emitting a [ResultContainer] containing a list of task reminders.
     */
    suspend fun getRemindersForTask(taskId: Long): Flow<ResultContainer<List<TaskReminder>>>

    /**
     * Creates a new task reminder.
     * @param reminder The task reminder to be created.
     */
    suspend fun createReminder(reminder: TaskReminder)

    /**
     * Deletes an existing task reminder.
     * @param reminder The task reminder to be deleted.
     */
    suspend fun deleteReminder(reminder: TaskReminder)

}