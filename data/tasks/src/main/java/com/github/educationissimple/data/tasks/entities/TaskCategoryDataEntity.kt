package com.github.educationissimple.data.tasks.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "task_categories",
    indices = [
        Index("name", unique = true)
    ]
)
class TaskCategoryDataEntity {
    @PrimaryKey(autoGenerate = true) val id: Long = 0
    val name: String? = null
}