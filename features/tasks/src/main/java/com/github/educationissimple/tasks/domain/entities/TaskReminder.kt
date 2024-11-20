package com.github.educationissimple.tasks.domain.entities

import java.time.LocalDateTime

/**
 * A data class representing a reminder associated with a task.
 *
 * @property id The unique identifier of the reminder.
 * @property taskId The unique identifier of the task associated with this reminder.
 * @property taskText A short description or text of the associated task.
 * @property datetime The date and time when the reminder is scheduled.
 */
data class TaskReminder(
    val id: Long = 0,
    val taskId: Long,
    val taskText: String,
    val datetime: LocalDateTime
)