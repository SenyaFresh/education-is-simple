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

    @Query("""SELECT * FROM tasks WHERE 
            date BETWEEN :startDate AND :endDate
            AND is_completed = :isCompleted
            AND (:categoryId IS NULL OR category_id = :categoryId)
            ORDER BY 
            CASE 
                WHEN :sortType = 'priority' THEN priority
                WHEN :sortType = 'date' THEN date
                WHEN :sortType = 'text' THEN text
                ELSE id END DESC""")
    suspend fun getTasks(
        startDate: LocalDate,
        endDate: LocalDate,
        isCompleted: Boolean,
        categoryId: Long?,
        sortType: String? = null
    ): List<TaskDataEntity>

}