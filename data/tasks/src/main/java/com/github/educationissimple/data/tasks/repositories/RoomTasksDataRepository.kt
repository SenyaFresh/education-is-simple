package com.github.educationissimple.data.tasks.repositories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.common.flow.LazyFlowLoaderFactory
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.sources.TasksDataSource
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class RoomTasksDataRepository @Inject constructor(
    private val tasksDataSource: TasksDataSource,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : TasksDataRepository {

    private var currentCategoryId: Long? = null
    private var currentSortType: String? = null

    private val previousTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksBeforeDate(LocalDate.now())
    }

    private val todayTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksByDate(LocalDate.now())
    }

    private val futureTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getTasksAfterDate(LocalDate.now())
    }

    private val completedTasksLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getCompletedTasks()
    }

    private val categoriesLoader = lazyFlowLoaderFactory.create {
        tasksDataSource.getCategories()
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
        updateSources(silently = false)
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
        currentSortType = sortType
        updateSources()
    }

    private fun updateSources(silently: Boolean = true) {
        previousTasksLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getTasksBeforeDate(
                    LocalDate.now(),
                    currentCategoryId,
                    currentSortType
                )
            },
            silently = silently
        )
        todayTasksLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getTasksByDate(
                    LocalDate.now(),
                    currentCategoryId,
                    currentSortType
                )
            },
            silently = silently
        )
        futureTasksLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getTasksAfterDate(
                    LocalDate.now(),
                    currentCategoryId,
                    currentSortType
                )
            },
            silently = silently
        )
        completedTasksLoader.newAsyncLoad(
            valueLoader = {
                tasksDataSource.getCompletedTasks(
                    currentCategoryId,
                    currentSortType
                )
            },
            silently = silently
        )
    }
}