package com.github.educationissimple.data.tasks.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.tuples.NewTaskTuple
import java.time.LocalDate

/**
 * DAO interface for interacting with the tasks table in the database.
 *
 * Provides methods for creating, updating, deleting tasks, and querying tasks based on various filters and sorting options.
 */
@Dao
interface TasksDao {

    /**
     * Inserts a new task into the database.
     *
     * @param newTaskTuple The data for the new task to be inserted.
     */
    @Insert(entity = TaskDataEntity::class)
    suspend fun createTask(newTaskTuple: NewTaskTuple)

    /**
     * Updates an existing task in the database.
     *
     * @param taskDataEntity The task data to be updated.
     */
    @Update(entity = TaskDataEntity::class)
    suspend fun updateTask(taskDataEntity: TaskDataEntity)

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to be deleted.
     */
    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTask(id: Long)

    /**
     * Retrieves a list of tasks based on the specified filters and sorting options.
     *
     * @param startDate The start date for the task search range.
     * @param endDate The end date for the task search range.
     * @param isCompleted A boolean indicating whether to filter tasks based on completion status.
     * @param categoryId The category ID to filter tasks by. If null, no category filter is applied.
     * @param searchText An optional search text to filter tasks by the task text.
     * @param sortType The sorting order for the tasks.
     *
     *  Can be one of the following:
     * - "date_asc": Sort by date in ascending order.
     * - "date_desc": Sort by date in descending order.
     * - "priority": Sort by priority in descending order.
     * - "text_asc": Sort by task text in ascending order.
     * - "text_desc": Sort by task text in descending order.
     *
     * @return A list of [TaskDataEntity] representing the tasks that match the filters and sorting criteria.
     */
    @Query(
        """SELECT * FROM tasks WHERE 
            date BETWEEN :startDate AND :endDate
            AND is_completed = :isCompleted
            AND (:categoryId IS NULL OR category_id = :categoryId)
            AND (:searchText IS NULL OR text LIKE '%' || :searchText || '%')
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
        searchText: String? = null,
        sortType: String? = null
    ): List<TaskDataEntity>

}