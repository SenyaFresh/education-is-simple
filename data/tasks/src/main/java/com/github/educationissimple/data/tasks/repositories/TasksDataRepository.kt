package com.github.educationissimple.data.tasks.repositories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.tuples.NewReminderTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.tuples.RemindersAndTasksTuple
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Repository interface for managing task-related data operations.
 *
 * This interface defines operations for interacting with task data, including retrieving tasks, updating them,
 * and managing categories and reminders. The methods provide access to different views of the task data,
 * such as not completed tasks, completed tasks, tasks for specific dates, and category operations.
 */
interface TasksDataRepository {

    /**
     * Changes the search query for tasks.
     *
     * @param query The search query string to filter tasks by. Pass null to clear the query.
     */
    suspend fun changeTasksSearchQuery(query: String?)

    /**
     * Changes the selected date for task filtering.
     *
     * @param date The selected date. Pass null to clear the selected date.
     */
    suspend fun changeSelectionDate(date: LocalDate?)

    /**
     * Retrieves tasks that are not completed for the selected date.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of not completed tasks for the selected date.
     */
    suspend fun getNotCompletedTasksForDate(): Flow<ResultContainer<List<TaskDataEntity>>>

    /**
     * Retrieves completed tasks for the selected date.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of completed tasks for the selected date.
     */
    suspend fun getCompletedTasksForDate(): Flow<ResultContainer<List<TaskDataEntity>>>

    /**
     * Retrieves previous tasks.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of tasks that are considered "previous."
     */
    suspend fun getPreviousTasks(): Flow<ResultContainer<List<TaskDataEntity>>>

    /**
     * Retrieves tasks for today.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of tasks for today.
     */
    suspend fun getTodayTasks(): Flow<ResultContainer<List<TaskDataEntity>>>

    /**
     * Retrieves future tasks.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of tasks scheduled for the future.
     */
    suspend fun getFutureTasks(): Flow<ResultContainer<List<TaskDataEntity>>>

    /**
     * Retrieves all completed tasks.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of all completed tasks.
     */
    suspend fun getCompletedTasks(): Flow<ResultContainer<List<TaskDataEntity>>>

    /**
     * Reloads all tasks from the data source.
     */
    suspend fun reloadTasks()

    /**
     * Retrieves the currently selected sort type for tasks.
     *
     * @return A [Flow] emitting a [ResultContainer] with the selected sort type string.
     */
    suspend fun getSelectedSortType(): Flow<ResultContainer<String?>>

    /**
     * Adds a new task.
     *
     * @param newTask The new task to add.
     */
    suspend fun addTask(newTask: NewTaskTuple)

    /**
     * Updates an existing task.
     *
     * @param updatedTask The task with updated data.
     */
    suspend fun updateTask(updatedTask: TaskDataEntity)

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to delete.
     */
    suspend fun deleteTask(id: Long)

    /**
     * Changes the task category.
     *
     * @param categoryId The ID of the category for the tasks. Pass null to reset the category.
     */
    suspend fun changeCategory(categoryId: Long?)

    /**
     * Retrieves all task categories.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of task categories.
     */
    suspend fun getCategories(): Flow<ResultContainer<List<TaskCategoryDataEntity>>>

    /**
     * Reloads all task categories from the data source.
     */
    suspend fun reloadCategories()

    /**
     * Retrieves the currently selected task category ID.
     *
     * @return A [Flow] emitting a [ResultContainer] with the selected task category ID.
     */
    suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>>

    /**
     * Creates a new task category.
     *
     * @param newTaskCategoryTuple The new task category to create.
     */
    suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple)

    /**
     * Deletes a task category by its ID.
     *
     * @param id The ID of the task category to delete.
     */
    suspend fun deleteCategory(id: Long)

    /**
     * Changes the sorting type for tasks.
     *
     * @param sortType The new sort type string to use for tasks.
     */
    suspend fun changeSortingType(sortType: String?)

    /**
     * Retrieves all task reminders.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of task reminders.
     */
    suspend fun getReminders(): Flow<ResultContainer<List<RemindersAndTasksTuple>>>

    /**
     * Retrieves task reminders for a specific task.
     *
     * @param taskId The ID of the task for which to retrieve reminders.
     * @return A [Flow] emitting a [ResultContainer] with a list of reminders for the specified task.
     */
    suspend fun getRemindersForTask(taskId: Long): Flow<ResultContainer<List<RemindersAndTasksTuple>>>

    /**
     * Reloads all task reminders from the data source.
     */
    suspend fun reloadReminders()

    /**
     * Creates a new reminder for a task.
     *
     * @param newReminderTuple The new reminder data for the task.
     * @return The ID of the created reminder.
     */
    suspend fun createTaskReminder(newReminderTuple: NewReminderTuple): Long

    /**
     * Deletes a task reminder by its ID.
     *
     * @param id The ID of the reminder to delete.
     */
    suspend fun deleteTaskReminder(id: Long)
}
