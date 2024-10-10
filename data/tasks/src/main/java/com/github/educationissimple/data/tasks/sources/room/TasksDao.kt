package com.github.educationissimple.data.tasks.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import com.github.educationissimple.data.tasks.tuples.TaskCompletionTuple

@Dao
interface TasksDao {

    @Update(entity = TaskDataEntity::class)
    suspend fun setTaskCompletion(taskCompletionTuple: TaskCompletionTuple)

    @Insert(entity = TaskDataEntity::class)
    suspend fun createTask(newTaskTuple: NewTaskTuple)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTask(id: Long)

    @Query("SELECT * FROM tasks WHERE date < :date")
    suspend fun getTasksBeforeDate(date: String): List<TaskDataEntity>

    @Query("SELECT * FROM tasks WHERE date = :date")
    suspend fun getTasksByDate(date: String): List<TaskDataEntity>

    @Query("SELECT * FROM tasks WHERE date > :date")
    suspend fun getTasksAfterDate(date: String): List<TaskDataEntity>

    @Query("SELECT * FROM tasks WHERE is_completed = 1")
    suspend fun getCompletedTasks(): List<TaskDataEntity>

}