package com.github.educationissimple.data.tasks.sources

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.tuples.TaskCompletionTuple
import java.time.LocalDate

interface TasksDataSource {

    suspend fun setTaskCompletion(taskCompletionTuple: TaskCompletionTuple)

    suspend fun createTask(newTaskTuple: NewTaskTuple)

    suspend fun deleteTask(id: Long)

    suspend fun getTasksBeforeDate(date: LocalDate): List<TaskDataEntity>

    suspend fun getTasksByDate(date: LocalDate): List<TaskDataEntity>

    suspend fun getTasksAfterDate(date: LocalDate): List<TaskDataEntity>

    suspend fun getCompletedTasks(): List<TaskDataEntity>

}