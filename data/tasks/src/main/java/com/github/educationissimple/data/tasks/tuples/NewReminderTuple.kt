package com.github.educationissimple.data.tasks.tuples

import androidx.room.ColumnInfo
import java.time.LocalDateTime

data class NewReminderTuple(
    @ColumnInfo(name = "task_id") val taskId: Long,
    val datetime: LocalDateTime
)