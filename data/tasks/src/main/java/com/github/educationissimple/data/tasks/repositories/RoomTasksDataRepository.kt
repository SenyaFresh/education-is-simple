package com.github.educationissimple.data.tasks.repositories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.common.flow.LazyFlowLoaderFactory
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.sources.TasksDataSource
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.tuples.TaskCompletionTuple
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class RoomTasksDataRepository @Inject constructor(
    private val tasksDataSource: TasksDataSource,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : TasksDataRepository {

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

    override suspend fun completeTask(id: Long) {
        tasksDataSource.setTaskCompletion(TaskCompletionTuple(id, true))
        updateSources()
    }

    override suspend fun cancelTaskCompletion(id: Long) {
        tasksDataSource.setTaskCompletion(TaskCompletionTuple(id, false))
        updateSources()
    }

    override suspend fun addTask(newTask: NewTaskTuple) {
        tasksDataSource.createTask(newTask)
        updateSources()
    }

    override suspend fun deleteTask(id: Long) {
        tasksDataSource.deleteTask(id)
        updateSources()
    }

    private fun updateSources() {
        previousTasksLoader.newAsyncLoad(silently = true)
        todayTasksLoader.newAsyncLoad(silently = true)
        futureTasksLoader.newAsyncLoad(silently = true)
        completedTasksLoader.newAsyncLoad(silently = true)
    }

}