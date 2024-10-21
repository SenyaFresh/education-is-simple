package com.github.educationissimple.data.tasks.sources

import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.tuples.TaskCompletionTuple
import java.time.LocalDate

interface TasksDataSource {

    suspend fun setTaskCompletion(taskCompletionTuple: TaskCompletionTuple)

    suspend fun createTask(newTaskTuple: NewTaskTuple)

    suspend fun updateTask(taskDataEntity: TaskDataEntity)

    suspend fun deleteTask(id: Long)

    suspend fun getTasksBeforeDate(date: LocalDate, categoryId: Long? = null): List<TaskDataEntity>

    suspend fun getTasksByDate(date: LocalDate, categoryId: Long? = null): List<TaskDataEntity>

    suspend fun getTasksAfterDate(date: LocalDate, categoryId: Long? = null): List<TaskDataEntity>

    suspend fun getCompletedTasks(categoryId: Long? = null): List<TaskDataEntity>

    suspend fun getCategories(): List<TaskCategoryDataEntity>

    suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple)

    suspend fun deleteCategory(id: Long)

}