package com.github.educationissimple.data.tasks.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.educationissimple.data.tasks.entities.TaskReminderDataEntity
import com.github.educationissimple.data.tasks.tuples.NewReminderTuple
import com.github.educationissimple.data.tasks.tuples.RemindersAndTasksTuple

/**
 * DAO interface for interacting with the task reminders table in the database.
 *
 * Provides methods for creating, deleting, and querying task reminders.
 */
@Dao
interface TasksRemindersDao {

    /**
     * Inserts a new task reminder into the database.
     *
     * @param newReminder The data for the new task reminder to be inserted.
     * @return The ID of the newly inserted reminder.
     */
    @Insert(entity = TaskReminderDataEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun createReminder(newReminder: NewReminderTuple): Long

    /**
     * Deletes a task reminder by its ID.
     *
     * @param id The ID of the reminder to be deleted.
     */
    @Query("DELETE FROM task_reminders WHERE id = :id")
    suspend fun deleteReminder(id: Long)

    /**
     * Retrieves a list of all task reminders along with the corresponding task data.
     *
     * The reminders are ordered by datetime and task ID in ascending order.
     *
     * @return A list of [RemindersAndTasksTuple] which contains the reminder and its associated task data.
     */
    @Transaction
    @Query("""SELECT * FROM task_reminders
        ORDER BY datetime, task_id ASC""")
    suspend fun getReminders(): List<RemindersAndTasksTuple>

}