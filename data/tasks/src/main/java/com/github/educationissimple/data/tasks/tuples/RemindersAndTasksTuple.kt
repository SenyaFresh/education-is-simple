package com.github.educationissimple.data.tasks.tuples

import androidx.room.Embedded
import androidx.room.Relation
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.entities.TaskReminderDataEntity

data class RemindersAndTasksTuple(
    @Embedded val reminder: TaskReminderDataEntity,
    @Relation(
        entityColumn = "id",
        parentColumn = "task_id"
    )
    val task: TaskDataEntity
)