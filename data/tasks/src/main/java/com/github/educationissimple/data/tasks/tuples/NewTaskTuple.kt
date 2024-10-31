package com.github.educationissimple.data.tasks.tuples

import androidx.room.ColumnInfo
import java.time.LocalDate

data class NewTaskTuple(
    val id: Long,
    val text: String,
    @ColumnInfo(name = "category_id") val categoryId: Long? = null,
    val priority: Int = 0,
    val date: LocalDate = LocalDate.now()
)