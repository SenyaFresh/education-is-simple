package com.github.educationissimple.data.tasks.tuples

import androidx.room.ColumnInfo


data class TaskCompletionTuple(
    val id: Long,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean
)