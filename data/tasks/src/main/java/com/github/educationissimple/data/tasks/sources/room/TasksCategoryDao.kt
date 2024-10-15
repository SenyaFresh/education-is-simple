package com.github.educationissimple.data.tasks.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple

@Dao
interface TasksCategoryDao {

    @Query("SELECT * FROM task_categories")
    suspend fun getCategories(): List<TaskCategoryDataEntity>

    @Insert(entity = TaskCategoryDataEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple)

    @Query("DELETE FROM task_categories WHERE id = :id")
    suspend fun deleteCategory(id: Long)

}