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

class RoomTasksDataRepository @Inject constructor(
    private val tasksDataSource: TasksDataSource,
    private val taskPreferencesDataSource: TaskPreferencesDataSource,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : TasksDataRepository {

    private var searchQuery: String? = null
    private var selectedDate: LocalDate = LocalDate.now()

    private var currentCategoryId: Long?
        get() = taskPreferencesDataSource.getSelectedCategoryId()
        set(value) = taskPreferencesDataSource.saveSelectedCategoryId(value)

    private var currentSortType: String?
        get() = taskPreferencesDataSource.getSortType()
        set(value) = taskPreferencesDataSource.saveSortType(value)

    private val selectedCategoryIdLoader = lazyFlowLoaderFactory.create {
        currentCategoryId
    }

    private val selectedSortTypeLoader = lazyFlowLoaderFactory.create {
        currentSortType
    }

    private val notCompletedTasksForDateLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksByDate(
            date = selectedDate,
            categoryId = currentCategoryId,
            searchText = searchQuery,
            sortType = currentSortType
        )
    }

    private val completedTasksForDateLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getCompletedTasks(
            date = selectedDate,
            categoryId = currentCategoryId,
            searchText = searchQuery,
            sortType = currentSortType
        )
    }

    private val previousTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksBeforeDate(
            date = LocalDate.now(),
            categoryId = currentCategoryId,
            searchText = searchQuery,
            sortType = currentSortType
        )
    }

    private val todayTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksByDate(
            date = LocalDate.now(),
            categoryId = currentCategoryId,
            searchText = searchQuery,
            sortType = currentSortType
        )
    }

    private val futureTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksAfterDate(
            date = LocalDate.now(),
            categoryId = currentCategoryId,
            searchText = searchQuery,
            sortType = currentSortType
        )
    }

    private val completedTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getCompletedTasks(
            categoryId = currentCategoryId,
            searchText = searchQuery,
            sortType = currentSortType
        )
    }

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

    override suspend fun changeSearchQuery(query: String?) {
        searchQuery = query
        updateSources()
    }

    override suspend fun changeSelectionDate(date: LocalDate?) {
        selectedDate = date ?: LocalDate.now()
        updateSources()
    }

    override suspend fun getCompletedTasksForDate(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return completedTasksForDateLoader.listen()
    }

    override suspend fun getNotCompletedTasksForDate(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return notCompletedTasksForDateLoader.listen()
    }

    override suspend fun getPreviousTasks(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return previousTasksLoader.listen()
    }

    override suspend fun getTodayTasks(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return todayTasksLoader.listen()
    }

    override suspend fun getFutureTasks(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return futureTasksLoader.listen()
    }

    override suspend fun getCompletedTasks(): Flow<ResultContainer<List<TaskDataEntity>>> {
        return completedTasksLoader.listen()
    }

    override suspend fun addTask(newTask: NewTaskTuple) {
        tasksDataSource.createTask(newTask)
        updateSources()
    }

    override suspend fun updateTask(updatedTask: TaskDataEntity) {
        tasksDataSource.updateTask(updatedTask)
        updateSources()
    }

    override suspend fun deleteTask(id: Long) {
        tasksDataSource.deleteTask(id)
        updateSources()
    }

    override suspend fun changeCategory(categoryId: Long?) {
        currentCategoryId = categoryId
        selectedCategoryIdLoader.newAsyncLoad(silently = true)
        updateSources()
    }

    override suspend fun getCategories(): Flow<ResultContainer<List<TaskCategoryDataEntity>>> {
        return categoriesLoader.listen()
    }

    override suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple) {
        tasksDataSource.createCategory(newTaskCategoryTuple)
        categoriesLoader.newAsyncLoad(silently = true)
    }

    override suspend fun deleteCategory(id: Long) {
        tasksDataSource.deleteCategory(id)
        if (currentCategoryId == id) {
            currentCategoryId = null
        }
        categoriesLoader.newAsyncLoad(silently = true)
        updateSources()
    }

    override suspend fun changeSortingType(sortType: String?) {
        currentSortType = sortType
        selectedSortTypeLoader.newAsyncLoad(silently = true)
        updateSources()
    }

    override suspend fun getReminders(): Flow<ResultContainer<List<RemindersAndTasksTuple>>> {
        return remindersLoader.listen()
    }

    override suspend fun getRemindersForTask(taskId: Long): Flow<ResultContainer<List<RemindersAndTasksTuple>>> {
        return remindersLoader.listen().map { container ->
            container.map { list ->
                list.filter { it.task.id == taskId }
            }
        }
    }

    override suspend fun createTaskReminder(newReminderTuple: NewReminderTuple) {
        tasksDataSource.createTaskReminder(newReminderTuple)
        remindersLoader.newAsyncLoad(silently = false)
    }

    override suspend fun deleteTaskReminder(id: Long) {
        tasksDataSource.deleteTaskReminder(id)
        remindersLoader.newAsyncLoad(silently = false)
    }

    private fun updateSources(silently: Boolean = false) {
        notCompletedTasksForDateLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getTasksByDate(
                    date = selectedDate,
                    categoryId = currentCategoryId,
                    searchText = searchQuery,
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
                    searchText = searchQuery,
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
                    searchText = searchQuery,
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
                    searchText = searchQuery,
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
                    searchText = searchQuery,
                    sortType = currentSortType
                )
            },
            silently = silently
        )
        completedTasksLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getCompletedTasks(
                    categoryId = currentCategoryId,
                    searchText = searchQuery,
                    sortType = currentSortType
                )
            },
            silently = silently
        )
    }
}