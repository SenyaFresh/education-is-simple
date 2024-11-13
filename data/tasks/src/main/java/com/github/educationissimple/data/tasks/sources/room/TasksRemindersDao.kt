package com.github.educationissimple.data.tasks.sources.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.educationissimple.data.tasks.entities.TaskReminderDataEntity
import com.github.educationissimple.data.tasks.tuples.NewReminderTuple
import com.github.educationissimple.data.tasks.tuples.RemindersAndTasksTuple

interface TasksRemindersDao {

    @Insert(entity = TaskReminderDataEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun createReminder(newReminder: NewReminderTuple)

    @Query("DELETE FROM taskReminders WHERE id = :id")
    suspend fun deleteReminder(id: Long)

    @Query("SELECT * FROM taskReminders")
    suspend fun getReminders(): List<RemindersAndTasksTuple>

}