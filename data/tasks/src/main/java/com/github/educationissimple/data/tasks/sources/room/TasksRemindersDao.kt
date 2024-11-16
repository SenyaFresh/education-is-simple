package com.github.educationissimple.data.tasks.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.github.educationissimple.data.tasks.entities.TaskReminderDataEntity
import com.github.educationissimple.data.tasks.tuples.NewReminderTuple
import com.github.educationissimple.data.tasks.tuples.RemindersAndTasksTuple

@Dao
interface TasksRemindersDao {

    @Insert(entity = TaskReminderDataEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun createReminder(newReminder: NewReminderTuple): Long

    @Query("DELETE FROM task_reminders WHERE id = :id")
    suspend fun deleteReminder(id: Long)

    @Transaction
    @Query("""SELECT * FROM task_reminders
        ORDER BY datetime, task_id ASC""")
    suspend fun getReminders(): List<RemindersAndTasksTuple>

}