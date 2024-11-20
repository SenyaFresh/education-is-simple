package com.github.educationissimple.data.tasks.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime


/**
 * Represents a task reminder in the database. This entity is used to store reminder information associated with
 * tasks. Each reminder is linked to a specific task, and the reminder has a date and time when it is scheduled.
 *
 * If a task is deleted, its associated reminders will also be deleted due to the `onDelete = ForeignKey.CASCADE` constraint.
 *
 * @property id The unique identifier for the reminder. This value is auto-generated.
 * @property taskId The identifier of the task this reminder is linked to.
 * @property datetime The date and time when the reminder is scheduled. Stored as [LocalDateTime].
 */
@Entity(
    tableName = "task_reminders",
    foreignKeys = [
        ForeignKey(
            entity = TaskDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["task_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class TaskReminderDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "task_id") val taskId: Long,
    val datetime: LocalDateTime
)