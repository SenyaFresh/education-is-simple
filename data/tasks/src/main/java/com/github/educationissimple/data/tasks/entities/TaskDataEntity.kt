package com.github.educationissimple.data.tasks.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tasks")
data class TaskDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val text: String,
    val date: LocalDate,
    @ColumnInfo(name = "is_completed", defaultValue = "0") val isCompleted: Boolean
)