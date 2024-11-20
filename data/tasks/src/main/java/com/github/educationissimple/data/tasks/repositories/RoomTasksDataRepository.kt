package com.github.educationissimple.data.tasks.repositories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.common.flow.LazyFlowLoaderFactory
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.sources.TaskPreferencesDataSource
import com.github.educationissimple.data.tasks.sources.TasksDataSource
import com.github.educationissimple.data.tasks.tuples.NewReminderTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.tuples.RemindersAndTasksTuple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

/**
 * A data repository for managing tasks and related data.
 * Implements the [TasksDataRepository] interface, performing operations for adding, updating, and deleting tasks,
 * as well as managing categories, reminders, and sorting preferences.
 *
 * @param tasksDataSource A data source for interacting with task-related data.
 * @param taskPreferencesDataSource A data source for managing user preferences (e.g., selected category, sorting type).
 * @param lazyFlowLoaderFactory A factory for creating lazy data loaders.
 */
class RoomTasksDataRepository @Inject constructor(
    private val tasksDataSource: TasksDataSource,
    private val taskPreferencesDataSource: TaskPreferencesDataSource,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : TasksDataRepository {

    private var tasksSearchQuery: String? = null
    private var selectedDate: LocalDate = LocalDate.now()

    // Current category ID and sort type management using preferences data source
    private var currentCategoryId: Long?
        get() = taskPreferencesDataSource.getSelectedCategoryId()
        set(value) = taskPreferencesDataSource.saveSelectedCategoryId(value)

    private var currentSortType: String?
        get() = taskPreferencesDataSource.getSortType()
        set(value) = taskPreferencesDataSource.saveSortType(value)

    // Lazy loaders for selected category ID and sort type.
    private val selectedCategoryIdLoader = lazyFlowLoaderFactory.create {
        currentCategoryId
    }

    private val selectedSortTypeLoader = lazyFlowLoaderFactory.create {
        currentSortType
    }

    // Lazy loaders for fetching tasks based on various filters.
    private val notCompletedTasksForDateLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksByDate(
            date = selectedDate,
            categoryId = currentCategoryId,
            searchText = tasksSearchQuery,
            sortType = currentSortType
        )
    }

    private val completedTasksForDateLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getCompletedTasks(
            date = selectedDate,
            categoryId = currentCategoryId,
            searchText = tasksSearchQuery,
            sortType = currentSortType
        )
    }

    private val previousTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksBeforeDate(
            date = LocalDate.now(),
            categoryId = currentCategoryId,
            searchText = tasksSearchQuery,
            sortType = currentSortType
        )
    }

    private val todayTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksByDate(
            date = LocalDate.now(),
            categoryId = currentCategoryId,
            searchText = tasksSearchQuery,
            sortType = currentSortType
        )
    }

    private val futureTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksAfterDate(
            date = LocalDate.now(),
            categoryId = currentCategoryId,
            searchText = tasksSearchQuery,
            sortType = currentSortType
        )
    }

    private val completedTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getCompletedTasks(
            categoryId = currentCategoryId,
            searchText = tasksSearchQuery,
            sortType = currentSortType
        )
    }

    // Lazy loaders for fetching categories and reminders.
    private val categoriesLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getCategories()
    }

    private val remindersLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getReminders()
    }

    override suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>> {
        return selectedCategoryIdLoader.listen()
    }

    override suspend fun getSelectedSortType(): Flow<ResultContainer<String?>> {
        return selectedSortTypeLoader.listen()
    }

    /**
     * Changes the search query for tasks.
     *
     * @param query The search query string to filter tasks by. Pass null to clear the query.
     */
    override suspend fun changeTasksSearchQuery(query: String?) {
        tasksSearchQuery = query
        updateSources()
    }

    /**
     * Changes the selected date for task filtering.
     *
     * @param date The selected date. Pass null to clear the selected date.
     */
    override suspend fun changeSelectionDate(date: LocalDate?) {
        selectedDate = date ?: LocalDate.now()
        updateSources()
    }

    /**
     * Retrieves completed tasks for the selected date.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of completed tasks for the selected date.
     */
    override suspend fun getCompletedTasksForDate(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return completedTasksForDateLoader.listen()
    }

    /**
     * Retrieves tasks that are not completed for the selected date.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of not completed tasks for the selected date.
     */
    override suspend fun getNotCompletedTasksForDate(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return notCompletedTasksForDateLoader.listen()
    }

    /**
     * Retrieves previous tasks.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of tasks that are considered "previous."
     */
    override suspend fun getPreviousTasks(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return previousTasksLoader.listen()
    }

    /**
     * Retrieves tasks for today.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of tasks for today.
     */
    override suspend fun getTodayTasks(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return todayTasksLoader.listen()
    }

    /**
     * Retrieves future tasks.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of tasks scheduled for the future.
     */
    override suspend fun getFutureTasks(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return futureTasksLoader.listen()
    }

    /**
     * Retrieves all completed tasks.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of all completed tasks.
     */
    override suspend fun getCompletedTasks(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return completedTasksLoader.listen()
    }

    /**
     * Reloads all tasks from the data source.
     */
    override suspend fun reloadTasks() {
        updateSources(silently = false)
    }

    /**
     * Adds a new task.
     *
     * @param newTask The new task to add.
     */
    override suspend fun addTask(newTask: NewTaskTuple) {
        tasksDataSource.createTask(newTask)
        updateSources()
    }

    /**
     * Updates an existing task.
     *
     * @param updatedTask The task with updated data.
     */
    override suspend fun updateTask(updatedTask: TaskDataEntity) {
        tasksDataSource.updateTask(updatedTask)
        updateSources()
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to delete.
     */
    override suspend fun deleteTask(id: Long) {
        tasksDataSource.deleteTask(id)
        updateSources()
    }

    /**
     * Changes the task category.
     *
     * @param categoryId The ID of the category for the tasks. Pass null to reset the category.
     */
    override suspend fun changeCategory(categoryId: Long?) {
        currentCategoryId = categoryId
        selectedCategoryIdLoader.newAsyncLoad(silently = true)
        updateSources()
    }

    /**
     * Retrieves all task categories.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of task categories.
     */
    override suspend fun getCategories(): Flow<ResultContainer<List<TaskCategoryDataEntity>>> {
        return categoriesLoader.listen()
    }

    /**
     * Reloads all task categories from the data source.
     */
    override suspend fun reloadCategories() {
        categoriesLoader.newAsyncLoad(silently = false)
    }

    /**
     * Creates a new task category.
     *
     * @param newTaskCategoryTuple The new task category to create.
     */
    override suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple) {
        tasksDataSource.createCategory(newTaskCategoryTuple)
        categoriesLoader.newAsyncLoad(silently = true)
    }

    /**
     * Deletes a task category by its ID.
     *
     * @param id The ID of the task category to delete.
     */
    override suspend fun deleteCategory(id: Long) {
        tasksDataSource.deleteCategory(id)
        if (currentCategoryId == id) {
            currentCategoryId = null
        }
        categoriesLoader.newAsyncLoad(silently = true)
        updateSources()
    }

    /**
     * Changes the sorting type for tasks.
     *
     * @param sortType The new sort type string to use for tasks.
     */
    override suspend fun changeSortingType(sortType: String?) {
        currentSortType = sortType
        selectedSortTypeLoader.newAsyncLoad(silently = true)
        updateSources()
    }


    /**
     * Retrieves all task reminders.
     *
     * @return A [Flow] emitting a [ResultContainer] with a list of task reminders.
     */
    override suspend fun getReminders(): Flow<ResultContainer<List<RemindersAndTasksTuple>>> {
        return remindersLoader.listen()
    }

    /**
     * Retrieves task reminders for a specific task.
     *
     * @param taskId The ID of the task for which to retrieve reminders.
     * @return A [Flow] emitting a [ResultContainer] with a list of reminders for the specified task.
     */
    override suspend fun getRemindersForTask(taskId: Long): Flow<ResultContainer<List<RemindersAndTasksTuple>>> {
        return remindersLoader.listen().map { container ->
            container.map { list ->
                list.filter { it.task.id == taskId }
            }
        }
    }

    /**
     * Reloads all task reminders from the data source.
     */
    override suspend fun reloadReminders() {
        remindersLoader.newAsyncLoad(silently = false)
    }

    /**
     * Creates a new reminder for a task.
     *
     * @param newReminderTuple The new reminder data for the task.
     * @return The ID of the created reminder.
     */
    override suspend fun createTaskReminder(newReminderTuple: NewReminderTuple): Long {
        val id = tasksDataSource.createTaskReminder(newReminderTuple)
        remindersLoader.newAsyncLoad(silently = false)
        return id
    }

    /**
     * Deletes a task reminder by its ID.
     *
     * @param id The ID of the reminder to delete.
     */
    override suspend fun deleteTaskReminder(id: Long) {
        tasksDataSource.deleteTaskReminder(id)
        remindersLoader.newAsyncLoad(silently = false)
    }

    /**
     * Utility function to refresh all sources after any update.
     *
     * @param silently If true, resultContainer wont be set to Loading state.
     */
    private fun updateSources(silently: Boolean = false) {
        notCompletedTasksForDateLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getTasksByDate(
                    date = selectedDate,
                    categoryId = currentCategoryId,
                    searchText = tasksSearchQuery,
                    sortType = currentSortType
                )
            },
            silently = silently
        )
        completedTasksForDateLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getCompletedTasks(
                    date = selectedDate,
                    categoryId = currentCategoryId,
                    searchText = tasksSearchQuery,
                    sortType = currentSortType
                )
            },
            silently = silently
        )
        previousTasksLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getTasksBeforeDate(
                    date = LocalDate.now(),
                    categoryId = currentCategoryId,
                    searchText = tasksSearchQuery,
                    sortType = currentSortType
                )
            },
            silently = silently
        )
        todayTasksLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getTasksByDate(
                    date = LocalDate.now(),
                    categoryId = currentCategoryId,
                    searchText = tasksSearchQuery,
                    sortType = currentSortType
                )
            },
            silently = silently
        )
        futureTasksLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getTasksAfterDate(
                    date = LocalDate.now(),
                    categoryId = currentCategoryId,
                    searchText = tasksSearchQuery,
                    sortType = currentSortType
                )
            },
            silently = silently
        )
        completedTasksLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getCompletedTasks(
                    categoryId = currentCategoryId,
                    searchText = tasksSearchQuery,
                    sortType = currentSortType
                )
            },
            silently = silently
        )
    }
}