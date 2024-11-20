package com.github.educationissimple.data.tasks.tuples

import androidx.room.ColumnInfo
import java.time.LocalDateTime

/**
 * A data class representing a new reminder.
 *
 * This class is used to encapsulate the information needed to create a new reminder for a task.
 * It contains the task ID and the date and time when the reminder should trigger.
 *
 * @param taskId The ID of the task to which this reminder is associated.
 * @param datetime The date and time when the reminder should trigger.
 */
data class NewReminderTuple(
    @ColumnInfo(name = "task_id") val taskId: Long,
    val datetime: LocalDateTime
)