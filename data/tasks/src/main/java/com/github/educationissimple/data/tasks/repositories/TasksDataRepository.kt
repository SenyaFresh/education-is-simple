package com.github.educationissimple.data.tasks.repositories

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import kotlinx.coroutines.flow.Flow

interface TasksDataRepository {

    suspend fun getPreviousTasks() : Flow<ResultContainer<List<TaskDataEntity>>>

    suspend fun getTodayTasks() : Flow<ResultContainer<List<TaskDataEntity>>>

    suspend fun getFutureTasks() : Flow<ResultContainer<List<TaskDataEntity>>>

    suspend fun getCompletedTasks() : Flow<ResultContainer<List<TaskDataEntity>>>

    suspend fun completeTask(id: Long)

    suspend fun cancelTaskCompletion(id: Long)

    suspend fun addTask(newTask: NewTaskTuple)

    suspend fun updateTask(updatedTask: TaskDataEntity)

    suspend fun deleteTask(id: Long)

    suspend fun changeCategory(categoryId: Long?)

    suspend fun getCategories(): Flow<ResultContainer<List<TaskCategoryDataEntity>>>

    suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple)

    suspend fun deleteCategory(id: Long)

}