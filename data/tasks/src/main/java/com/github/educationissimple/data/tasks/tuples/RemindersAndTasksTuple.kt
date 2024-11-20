package com.github.educationissimple.data.tasks.tuples

import androidx.room.Embedded
import androidx.room.Relation
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.data.tasks.entities.TaskReminderDataEntity

/**
 * A data class representing a tuple of reminders and tasks.
 *
 * This class is used to encapsulate a reminder and the associated task.
 * It contains an embedded reminder entity and a related task entity.
 *
 * @param reminder The reminder entity associated with the task.
 * @param task The task entity associated with the reminder.
 */
data class RemindersAndTasksTuple(
    @Embedded val reminder: TaskReminderDataEntity,
    @Relation(
        entityColumn = "id",
        parentColumn = "task_id"
    )
    val task: TaskDataEntity
)