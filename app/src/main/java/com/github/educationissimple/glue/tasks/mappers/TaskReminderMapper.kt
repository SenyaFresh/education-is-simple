package com.github.educationissimple.glue.tasks.mappers

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.data.tasks.tuples.NewReminderTuple
import com.github.educationissimple.data.tasks.tuples.RemindersAndTasksTuple
import com.github.educationissimple.notifications.enities.ReminderItem
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Extension function to map a [RemindersAndTasksTuple] to a [TaskReminder].
 */
fun RemindersAndTasksTuple.mapToTaskReminder(): TaskReminder {
    return TaskReminder(
        id = reminder.id,
        taskId = reminder.taskId,
        datetime = reminder.datetime,
        taskText = task.text
    )
}

/**
 * Extension function to map a [Flow] of [ResultContainer] of [RemindersAndTasksTuple] to a [Flow] of [ResultContainer] of [TaskReminder].
 */
fun Flow<ResultContainer<List<RemindersAndTasksTuple>>>.mapToTaskReminder(): Flow<ResultContainer<List<TaskReminder>>> {
    return this.map { container ->
        container.map { list ->
            list.map { it.mapToTaskReminder() }
        }
    }
}

/**
 * Extension function to map a [TaskReminder] to a [NewReminderTuple].
 */
fun TaskReminder.toNewReminderTuple(): NewReminderTuple {
    return NewReminderTuple(
        taskId = taskId,
        datetime = datetime
    )
}

/**
 * Extension function to map a [TaskReminder] to a [ReminderItem].
 */
fun TaskReminder.toReminderItem(): ReminderItem {
    return ReminderItem(
        id = id,
        text = taskText,
        datetime = datetime
    )
}