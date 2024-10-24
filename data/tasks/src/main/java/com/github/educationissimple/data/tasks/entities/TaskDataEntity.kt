package com.github.educationissimple.data.tasks.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = TaskCategoryDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["category_id"])]
)
data class TaskDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long?,
    val text: String,
    @ColumnInfo(defaultValue = "0") val priority: Int,
    val date: LocalDate,
    @ColumnInfo(name = "is_completed", defaultValue = "0") val isCompleted: Boolean
)