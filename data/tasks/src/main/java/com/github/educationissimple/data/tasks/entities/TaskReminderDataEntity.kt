package com.github.educationissimple.data.tasks.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.time.LocalDateTime


@Entity(
    tableName = "taskReminders",
)
data class TaskReminderDataEntity(
    val id: Long,
    @ColumnInfo(name = "task_id") val taskId: Long,
    val datetime: LocalDateTime
)