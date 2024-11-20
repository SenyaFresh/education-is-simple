package com.github.educationissimple.data.tasks.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

/**
 * Represents a task in the database. This entity is used to store tasks with attributes such as
 * a unique identifier, the associated task category, text, description, priority, date, and completion status.
 *
 * @property id The unique identifier for the task. This value is automatically generated.
 * @property categoryId The identifier of the task category this task belongs to. This is a nullable field.
 * @property text The main text of the task, typically describing the task.
 * @property description A more detailed description of the task, can be null.
 * @property priority An integer value indicating the task's priority. Default is 0 (no priority).
 * @property date The date when the task was created or is scheduled for. Stored as [LocalDate].
 * @property isCompleted Boolean indicating whether the task is completed. Default is false (0).
 */
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
    val description: String?,
    @ColumnInfo(defaultValue = "0") val priority: Int,
    val date: LocalDate,
    @ColumnInfo(name = "is_completed", defaultValue = "0") val isCompleted: Boolean
)