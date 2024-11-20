package com.github.educationissimple.data.tasks.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.data.tasks.tuples.NewTaskCategoryTuple

/**
 * DAO interface for interacting with the task categories table in the database.
 *
 * Provides methods for retrieving, inserting, and deleting task categories.
 */
@Dao
interface TasksCategoryDao {

    /**
     * Retrieves all task categories from the database.
     *
     * @return A list of [TaskCategoryDataEntity] representing all the task categories.
     */
    @Query("SELECT * FROM task_categories")
    suspend fun getCategories(): List<TaskCategoryDataEntity>

    /**
     * Inserts a new task category into the database.
     * If a category with the same name already exists, the insertion will be aborted.
     *
     * @param newTaskCategoryTuple The data for the new task category to be inserted.
     */
    @Insert(entity = TaskCategoryDataEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun createCategory(newTaskCategoryTuple: NewTaskCategoryTuple)

    /**
     * Deletes a task category by its ID.
     *
     * @param id The ID of the task category to be deleted.
     */
    @Query("DELETE FROM task_categories WHERE id = :id")
    suspend fun deleteCategory(id: Long)

}