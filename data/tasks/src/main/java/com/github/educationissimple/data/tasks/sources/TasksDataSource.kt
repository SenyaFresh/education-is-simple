package com.github.educationissimple.data.tasks.sources

import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.tuples.NewReminderTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.tuples.RemindersAndTasksTuple
import java.time.LocalDate

/**
 * Interface that defines the operations for interacting with task-related data.
 *
 * This interface includes methods for creating, updating, deleting tasks,
 * retrieving tasks based on date and other filters, and managing task categories and reminders.
 */
interface TasksDataSource {

    /**
     * Creates a new task in the database.
     *
     * @param newTaskTuple A [NewTaskTuple] object containing the data for the new task.
     */
    suspend fun createTask(newTaskTuple: NewTaskTuple)

    /**
     * Updates an existing task in the database.
     *
     * @param taskDataEntity The [TaskDataEntity] object with the updated task information.
     */
    suspend fun updateTask(taskDataEntity: TaskDataEntity)

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to be deleted.
     */
    suspend fun deleteTask(id: Long)

    /**
     * Retrieves tasks that are scheduled before a specific date.
     *
     * @param date The date before which the tasks are retrieved.
     * @param categoryId The ID of the task category to filter by (optional).
     * @param searchText A text search filter for task names (optional).
     * @param sortType The type of sorting to apply to the results (optional).
     * @return A list of [TaskDataEntity] objects representing tasks before the given date.
     */
    suspend fun getTasksBeforeDate(
        date: LocalDate,
        categoryId: Long? = null,
        searchText: String? = null,
        sortType: String? = null
    ): List<TaskDataEntity>

    /**
     * Retrieves tasks that are scheduled for a specific date.
     *
     * @param date The date for which the tasks are retrieved.
     * @param categoryId The ID of the task category to filter by (optional).
     * @param searchText A text search filter for task names (optional).
     * @param sortType The type of sorting to apply to the results (optional).
     * @return A list of [TaskDataEntity] objects representing tasks on the given date.
     */
    suspend fun getTasksByDate(
        date: LocalDate,
        categoryId: Long? = null,
        searchText: String? = null,
        sortType: String? = null
    ): List<TaskDataEntity>

    /**
     * Retrieves tasks that are scheduled after a specific date.
     *
     * @param date The date after which the tasks are retrieved.
     * @param categoryId The ID of the task category to filter by (optional).
     * @param searchText A text search filter for task names (optional).
     * @param sortType The type of sorting to apply to the results (optional).
     * @return A list of [TaskDataEntity] objects representing tasks after the given date.
     */
    suspend fun getTasksAfterDate(
        date: LocalDate,
        categoryId: Long? = null,
        searchText: String? = null,
        sortType: String? = null
    ): List<TaskDataEntity>

    /**
     * Retrieves all completed tasks, optionally filtered by date, category, search text, and sort type.
     *
     * @param date The date to filter completed tasks by (optional).
     * @param categoryId The ID of the task category to filter by (optional).
     * @param searchText A text search filter for task names (optional).
     * @param sortType The type of sorting to apply to the results (optional).
     * @return A list of [TaskDataEntity] objects representing completed tasks.
     */
    suspend fun getCompletedTasks(
        date: LocalDate? = null,
        categoryId: Long? = null,
        searchText: String? = null,
        sortType: String? = null,
    ): List<TaskDataEntity>

    /**
     * Retrieves all task categories.
     *
     * @return A list of [TaskCategoryDataEntity] objects representing all task categories.
     */
    suspend fun getCategories(): List<TaskCategoryDataEntity>

    /**
     * Creates a new task category.
     *
     * @param newTaskCategoryTuple A [NewTaskCategoryTuple] object containing the new category's data.
     */
    suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple)

    /**
     * Deletes a task category by its ID.
     *
     * @param id The ID of the task category to be deleted.
     */
    suspend fun deleteCategory(id: Long)

    /**
     * Retrieves all task reminders.
     *
     * @return A list of [RemindersAndTasksTuple] objects representing task reminders and their associated tasks.
     */
    suspend fun getReminders(): List<RemindersAndTasksTuple>

    /**
     * Creates a new task reminder.
     *
     * @param newReminderTuple A [NewReminderTuple] object containing the new reminder's data.
     * @return The ID of the newly created task reminder.
     */
    suspend fun createTaskReminder(newReminderTuple: NewReminderTuple): Long

    /**
     * Deletes a task reminder by its ID.
     *
     * @param id The ID of the task reminder to be deleted.
     */
    suspend fun deleteTaskReminder(id: Long)

}