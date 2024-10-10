package com.github.educationissimple.tasks.domain.repositories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    suspend fun getPreviousTasks() : Flow<ResultContainer<List<Task>>>

    suspend fun getTodayTasks() : Flow<ResultContainer<List<Task>>>

    suspend fun getFutureTasks() : Flow<ResultContainer<List<Task>>>

    suspend fun getCompletedTasks() : Flow<ResultContainer<List<Task>>>

    suspend fun completeTask(task: Task)

    suspend fun cancelTask(task: Task)

    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)

}