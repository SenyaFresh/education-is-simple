package com.github.educationissimple.data.tasks.tuples

import androidx.room.ColumnInfo
import java.time.LocalDate

/**
 * A data class representing a new task.
 *
 * This class is used to represent the details needed to create a new task.
 * It contains the task ID, text, category ID, priority, and due date.
 *
 * @param id The unique identifier of the task.
 * @param text The textual description of the task.
 * @param categoryId The ID of the category to which the task belongs. Default is null.
 * @param priority The priority level of the task. Default is 0.
 * @param date The due date of the task. Default is the current date.
 */
data class NewTaskTuple(
    val id: Long,
    val text: String,
    @ColumnInfo(name = "category_id") val categoryId: Long? = null,
    val priority: Int = 0,
    val date: LocalDate = LocalDate.now()
)