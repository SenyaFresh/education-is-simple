package com.github.educationissimple.data.tasks.repositories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.common.flow.LazyFlowLoaderFactory
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.sources.PreferencesDataSource
import com.github.educationissimple.data.tasks.sources.TasksDataSource
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class RoomTasksDataRepository @Inject constructor(
    private val tasksDataSource: TasksDataSource,
    private val preferencesDataSource: PreferencesDataSource,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : TasksDataRepository {

    private var searchQuery: String? = null

    private var currentCategoryId: Long?
        get() = preferencesDataSource.getSelectedCategoryId()
        set(value) = preferencesDataSource.saveSelectedCategoryId(value)

    private var currentSortType: String?
        get() = preferencesDataSource.getSortType()
        set(value) = preferencesDataSource.saveSortType(value)

    private val selectedCategoryIdLoader = lazyFlowLoaderFactory.create {
        currentCategoryId
    }

    override suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>> {
        return selectedCategoryIdLoader.listen()
    }

    private val selectedSortTypeLoader = lazyFlowLoaderFactory.create {
        currentSortType
    }

    override suspend fun getSelectedSortType(): Flow<ResultContainer<String?>> {
        return selectedSortTypeLoader.listen()
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

    override suspend fun changeSearchQuery(query: String?) {
        searchQuery = query
        updateSources()
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
        preferencesDataSource.saveSelectedCategoryId(categoryId)
        currentCategoryId = categoryId
        selectedCategoryIdLoader.newAsyncLoad(silently = true)
        updateSources(false)
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
        categoriesLoader.newAsyncLoad(silently = true)
    }

    override suspend fun changeSortingType(sortType: String?) {
        preferencesDataSource.saveSortType(sortType)
        currentSortType = sortType
        selectedSortTypeLoader.newAsyncLoad(silently = true)
        updateSources()
    }

    private fun updateSources(silently: Boolean = true) {
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