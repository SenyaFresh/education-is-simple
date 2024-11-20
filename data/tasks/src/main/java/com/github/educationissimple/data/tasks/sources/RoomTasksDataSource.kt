package com.github.educationissimple.data.tasks.sources

import android.content.Context
import androidx.room.Room
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.sources.room.TasksDatabase
import com.github.educationissimple.data.tasks.tuples.NewReminderTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.tuples.RemindersAndTasksTuple
import com.github.educationissimple.data.tasks.utils.getMaxDate
import com.github.educationissimple.data.tasks.utils.getMinDate
import java.time.LocalDate
import javax.inject.Inject

/**
 * Implementation of [TasksDataSource] that uses Room database for task and reminder management.
 *
 * This class provides methods to interact with tasks, categories, and reminders stored in a Room database.
 * It includes functionality for creating, updating, deleting tasks, as well as retrieving tasks based on various filters.
 */
class RoomTasksDataSource @Inject constructor(
    context: Context
) : TasksDataSource {

    /**
     * Room database instance for managing tasks, categories, and reminders.
     * Created from an asset database file.
     */
    private val db: TasksDatabase by lazy<TasksDatabase> {
        Room.databaseBuilder(
            context,
            TasksDatabase::class.java,
            "tasks.db"
        )
            .createFromAsset("initial_tasks_database.db")
            .build()
    }

    private val tasksDao = db.getTasksDao()
    private val tasksCategoryDao = db.getTasksCategoryDao()
    private val tasksRemindersDao = db.getTasksRemindersDao()

    /**
     * Creates a new task in the database.
     *
     * @param newTaskTuple A [NewTaskTuple] object containing the data for the new task.
     */
    override suspend fun createTask(newTaskTuple: NewTaskTuple) {
        tasksDao.createTask(newTaskTuple)
    }

    /**
     * Updates an existing task in the database.
     *
     * @param taskDataEntity The [TaskDataEntity] object with the updated task information.
     */
    override suspend fun updateTask(taskDataEntity: TaskDataEntity) {
        tasksDao.updateTask(taskDataEntity)
    }

    /**
     * Deletes a task by its ID from the database.
     *
     * @param id The ID of the task to be deleted.
     */
    override suspend fun deleteTask(id: Long) {
        tasksDao.deleteTask(id)
    }

    /**
     * Retrieves tasks that are scheduled before a specific date.
     *
     * @param date The date before which the tasks are retrieved.
     * @param categoryId The ID of the task category to filter by (optional).
     * @param searchText A text search filter for task names (optional).
     * @param sortType The type of sorting to apply to the results (optional).
     * @return A list of [TaskDataEntity] objects representing tasks before the given date.
     */
    override suspend fun getTasksBeforeDate(
        date: LocalDate,
        categoryId: Long?,
        searchText: String?,
        sortType: String?
    ): List<TaskDataEntity> {
        return tasksDao.getTasks(
            startDate = getMinDate(),
            endDate = date.minusDays(1),
            isCompleted = false,
            categoryId = categoryId,
            searchText = searchText,
            sortType = sortType
        )
    }

    /**
     * Retrieves tasks that are scheduled for a specific date.
     *
     * @param date The date for which the tasks are retrieved.
     * @param categoryId The ID of the task category to filter by (optional).
     * @param searchText A text search filter for task names (optional).
     * @param sortType The type of sorting to apply to the results (optional).
     * @return A list of [TaskDataEntity] objects representing tasks on the given date.
     */
    override suspend fun getTasksByDate(
        date: LocalDate,
        categoryId: Long?,
        searchText: String?,
        sortType: String?
    ): List<TaskDataEntity> {
        return tasksDao.getTasks(
            startDate = date,
            endDate = date,
            isCompleted = false,
            categoryId = categoryId,
            searchText = searchText,
            sortType = sortType
        )
    }

    /**
     * Retrieves tasks that are scheduled after a specific date.
     *
     * @param date The date after which the tasks are retrieved.
     * @param categoryId The ID of the task category to filter by (optional).
     * @param searchText A text search filter for task names (optional).
     * @param sortType The type of sorting to apply to the results (optional).
     * @return A list of [TaskDataEntity] objects representing tasks after the given date.
     */
    override suspend fun getTasksAfterDate(
        date: LocalDate,
        categoryId: Long?,
        searchText: String?,
        sortType: String?
    ): List<TaskDataEntity> {
        return tasksDao.getTasks(
            startDate = date.plusDays(1),
            endDate = getMaxDate(),
            isCompleted = false,
            categoryId = categoryId,
            searchText = searchText,
            sortType = sortType
        )
    }

    /**
     * Retrieves completed tasks, optionally filtered by date, category, search text, and sort type.
     *
     * @param date The date to filter completed tasks by (optional).
     * @param categoryId The ID of the task category to filter by (optional).
     * @param searchText A text search filter for task names (optional).
     * @param sortType The type of sorting to apply to the results (optional).
     * @return A list of [TaskDataEntity] objects representing completed tasks.
     */
    override suspend fun getCompletedTasks(
        date: LocalDate?,
        categoryId: Long?,
        searchText: String?,
        sortType: String?
    ): List<TaskDataEntity> {
        return tasksDao.getTasks(
            startDate = date ?: getMinDate(),
            endDate = date ?: getMaxDate(),
            isCompleted = true,
            categoryId = categoryId,
            searchText = searchText,
            sortType = sortType
        )
    }

    /**
     * Retrieves all task categories.
     *
     * @return A list of [TaskCategoryDataEntity] objects representing all task categories.
     */
    override suspend fun getCategories(): List<TaskCategoryDataEntity> {
        return tasksCategoryDao.getCategories()
    }

    /**
     * Creates a new task category in the database.
     *
     * @param newTaskCategoryTuple A [NewTaskCategoryTuple] object containing the data for the new task category.
     */
    override suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple) {
        return tasksCategoryDao.createCategory(newTaskCategoryTuple)
    }

    /**
     * Deletes a task category by its ID from the database.
     *
     * @param id The ID of the task category to be deleted.
     */
    override suspend fun deleteCategory(id: Long) {
        tasksCategoryDao.deleteCategory(id)
    }

    /**
     * Retrieves all task reminders.
     *
     * @return A list of [RemindersAndTasksTuple] objects representing task reminders and their associated tasks.
     */
    override suspend fun getReminders(): List<RemindersAndTasksTuple> {
        return tasksRemindersDao.getReminders()
    }

    /**
     * Creates a new task reminder in the database.
     *
     * @param newReminderTuple A [NewReminderTuple] object containing the data for the new task reminder.
     * @return The ID of the newly created task reminder.
     */
    override suspend fun createTaskReminder(newReminderTuple: NewReminderTuple): Long {
        return tasksRemindersDao.createReminder(newReminderTuple)
    }

    /**
     * Deletes a task reminder by its ID from the database.
     *
     * @param id The ID of the task reminder to be deleted.
     */
    override suspend fun deleteTaskReminder(id: Long) {
        tasksRemindersDao.deleteReminder(id)
    }

}