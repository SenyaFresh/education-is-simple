package com.github.educationissimple.data.tasks.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents a task category in the database. This entity is used to store information about the categories
 * of tasks in the application, including the category's name and a unique identifier.
 *
 * @property id The unique identifier for the task category. This value is automatically generated.
 * @property name The name of the task category. This value is stored in a case-insensitive manner.
 */
@Entity(
    tableName = "task_categories",
    indices = [
        Index("name", unique = true)
    ]
)
data class TaskCategoryDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val name: String
)