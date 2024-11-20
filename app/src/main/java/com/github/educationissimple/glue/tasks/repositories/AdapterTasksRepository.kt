package com.github.educationissimple.glue.tasks.repositories

import android.database.sqlite.SQLiteConstraintException
import com.github.educationissimple.R
import com.github.educationissimple.common.Core
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.common.UserFriendlyException
import com.github.educationissimple.data.tasks.repositories.TasksDataRepository
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.glue.tasks.mappers.mapToSortType
import com.github.educationissimple.glue.tasks.mappers.mapToTask
import com.github.educationissimple.glue.tasks.mappers.mapToTaskCategory
import com.github.educationissimple.glue.tasks.mappers.mapToTaskDataEntity
import com.github.educationissimple.glue.tasks.mappers.mapToTaskReminder
import com.github.educationissimple.glue.tasks.mappers.toNewReminderTuple
import com.github.educationissimple.glue.tasks.mappers.toReminderItem
import com.github.educationissimple.notifications.schedulers.ReminderScheduler
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

/**
 * Adapter class that implements the [TasksRepository] interface and adapts data operations from
 * the [TasksDataRepository] and reminder scheduling functionality from [ReminderScheduler] to
 * business logic.
 *
 * This class provides various methods to manage tasks, including retrieval, creation, and update of
 * tasks, categories, and reminders. It also provides sorting and filtering functionalities for tasks.
 *
 * @param tasksDataRepository The underlying repository responsible for the task data operations (CRUD).
 * @param reminderScheduler The reminder scheduler for managing task reminders.
 */
class AdapterTasksRepository @Inject constructor(
    private val tasksDataRepository: TasksDataRepository,
    private val reminderScheduler: ReminderScheduler,
) : TasksRepository {

    /**
     * Changes the date of selection for tasks.
     *
     * @param date The new date to select tasks for.
     */
    override suspend fun changeSelectionDate(date: LocalDate) {
        tasksDataRepository.changeSelectionDate(date)
    }

    /**
     * Fetches the list of tasks that are not completed for the selected date.
     *
     * @return A [Flow] containing a result container with a list of not completed tasks.
     */
    override suspend fun getNotCompletedTasksForDate(): Flow<ResultContainer<List<Task>>> {
        return tasksDataRepository.getNotCompletedTasksForDate().mapToTask()
    }

    /**
     * Fetches the list of completed tasks for the selected date.
     *
     * @return A [Flow] containing a result container with a list of completed tasks.
     */
    override suspend fun getCompletedTasksForDate(): Flow<ResultContainer<List<Task>>> {
        return tasksDataRepository.getCompletedTasksForDate().mapToTask()
    }

    /**
     * Fetches the list of previous tasks.
     *
     * @return A [Flow] containing a result container with a list of previous tasks.
     */
    override suspend fun getPreviousTasks(): Flow<ResultContainer<List<Task>>> {
        return tasksDataRepository.getPreviousTasks().mapToTask()
    }

    /**
     * Fetches the list of today's tasks.
     *
     * @return A [Flow] containing a result container with a list of today's tasks.
     */
    override suspend fun getTodayTasks(): Flow<ResultContainer<List<Task>>> {
        return tasksDataRepository.getTodayTasks().mapToTask()
    }

    /**
     * Fetches the list of future tasks.
     *
     * @return A [Flow] containing a result container with a list of future tasks.
     */
    override suspend fun getFutureTasks(): Flow<ResultContainer<List<Task>>> {
        return tasksDataRepository.getFutureTasks().mapToTask()
    }

    /**
     * Fetches the list of completed tasks.
     *
     * @return A [Flow] containing a result container with a list of completed tasks.
     */
    override suspend fun getCompletedTasks(): Flow<ResultContainer<List<Task>>> {
        return tasksDataRepository.getCompletedTasks().mapToTask()
    }

    /**
     * Reloads all tasks from the data source.
     */
    override suspend fun reloadTasks() {
        tasksDataRepository.reloadTasks()
    }

    /**
     * Changes the search query for tasks.
     *
     * @param text The search query string to filter tasks by.
     */
    override suspend fun changeTaskSearchText(text: String) {
        tasksDataRepository.changeTasksSearchQuery(text)
    }

    /**
     * Retrieves the currently selected sort type for tasks.
     *
     * @return A [Flow] emitting a [ResultContainer] with the selected sort type.
     */
    override suspend fun getSelectedSortType(): Flow<ResultContainer<SortType?>> {
        return tasksDataRepository.getSelectedSortType().mapToSortType()
    }

    /**
     * Adds a new task.
     *
     * @param task The new task to add.
     */
    override suspend fun addTask(task: Task) {
        tasksDataRepository.addTask(
            NewTaskTuple(
                id = task.id,
                text = task.text,
                categoryId = task.categoryId,
                date = task.date ?: LocalDate.now(),
                priority = task.priority.value
            )
        )
    }

    /**
     * Updates an existing task.
     *
     * @param task The task with updated data.
     */
    override suspend fun updateTask(task: Task) {
        tasksDataRepository.updateTask(task.mapToTaskDataEntity())
    }

    /**
     * Deletes a task by its ID.
     *
     * @param taskId The ID of the task to delete.
     */
    override suspend fun deleteTask(taskId: Long) {
        tasksDataRepository.deleteTask(taskId)
    }

    /**
     * Retrieves all task categories.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of task categories.
     */
    override suspend fun getCategories(): Flow<ResultContainer<List<TaskCategory>>> {
        return tasksDataRepository.getCategories().mapToTaskCategory()
    }

    /**
     * Reloads all task categories from the data source.
     */
    override suspend fun reloadCategories() {
        tasksDataRepository.reloadCategories()
    }

    /**
     * Retrieves the currently selected task category ID.
     *
     * @return A [Flow] emitting a [ResultContainer] with the selected task category ID.
     */
    override suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>> {
        return tasksDataRepository.getSelectedCategoryId()
    }

    /**
     * Creates a new task category.
     *
     * @param name The name of the category to create.
     */
    override suspend fun createCategory(name: String) {
        try {
            tasksDataRepository.createCategory(
                NewTaskCategoryTuple(
                    name.lowercase().trim()
                )
            )
        } catch (exception: SQLiteConstraintException) {
            throw UserFriendlyException(
                userFriendlyMessage = Core.resources.getString(R.string.category_constraint_exception),
                cause = exception
            )
        }
    }

    /**
     * Deletes a task category by its ID.
     *
     * @param categoryId The ID of the category to delete.
     */
    override suspend fun deleteCategory(categoryId: Long) {
        tasksDataRepository.deleteCategory(categoryId)
    }

    /**
     * Changes the task category.
     *
     * @param categoryId The ID of the category for the tasks. Pass null to reset the category.
     */
    override suspend fun changeCategory(categoryId: Long?) {
        tasksDataRepository.changeCategory(categoryId)
    }

    /**
     * Retrieves all task reminders.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of task reminders.
     */
    override suspend fun getAllReminders(): Flow<ResultContainer<List<TaskReminder>>> {
        return tasksDataRepository.getReminders().mapToTaskReminder()
    }

    /**
     * Reloads all task reminders from the data source.
     */
    override suspend fun reloadReminders() {
        tasksDataRepository.reloadReminders()
    }

    /**
     * Retrieves task reminders for a specific task.
     *
     * @param taskId The ID of the task for which to retrieve reminders.
     * @return A [Flow] emitting a [ResultContainer] with a list of reminders for the specified task.
     */
    override suspend fun getRemindersForTask(taskId: Long): Flow<ResultContainer<List<TaskReminder>>> {
        return tasksDataRepository.getRemindersForTask(taskId).mapToTaskReminder()
    }

    /**
     * Creates a new reminder for a task.
     *
     * @param reminder The task reminder to create.
     */
    override suspend fun createReminder(reminder: TaskReminder) {
        val id = tasksDataRepository.createTaskReminder(reminder.toNewReminderTuple())
        reminderScheduler.schedule(reminder.copy(id = id).toReminderItem())
    }

    /**
     * Deletes a task reminder.
     *
     * @param reminder The task reminder to delete.
     */
    override suspend fun deleteReminder(reminder: TaskReminder) {
        reminderScheduler.cancel(reminder.toReminderItem())
        tasksDataRepository.deleteTaskReminder(reminder.id)
    }

    /**
     * Changes the sorting type for tasks.
     *
     * @param sortType The new sorting type to use for tasks.
     */
    override suspend fun changeSortType(sortType: SortType?) {
        tasksDataRepository.changeSortingType(sortType?.value)
    }

}