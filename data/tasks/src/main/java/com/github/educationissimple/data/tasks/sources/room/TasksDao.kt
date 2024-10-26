package com.github.educationissimple.data.tasks.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import java.time.LocalDate

@Dao
interface TasksDao {

    @Insert(entity = TaskDataEntity::class)
    suspend fun createTask(newTaskTuple: NewTaskTuple)

    @Update(entity = TaskDataEntity::class)
    suspend fun updateTask(taskDataEntity: TaskDataEntity)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTask(id: Long)

    @Query(
        """SELECT * FROM tasks WHERE 
            date BETWEEN :startDate AND :endDate
            AND is_completed = :isCompleted
            AND (:categoryId IS NULL OR category_id = :categoryId)
            ORDER BY 
            CASE WHEN :sortType = 'date_asc' THEN date END ASC,
            CASE WHEN :sortType = 'date_desc' THEN date END DESC,
            CASE WHEN :sortType = 'priority' THEN priority END DESC,
            CASE WHEN :sortType = 'text_asc' THEN text END ASC,
            CASE WHEN :sortType = 'text_desc' THEN text END DESC,
            CASE WHEN :sortType IS NULL THEN id END DESC"""
    )
    suspend fun getTasks(
        startDate: LocalDate,
        endDate: LocalDate,
        isCompleted: Boolean,
        categoryId: Long?,
        sortType: String? = null
    ): List<TaskDataEntity>

}