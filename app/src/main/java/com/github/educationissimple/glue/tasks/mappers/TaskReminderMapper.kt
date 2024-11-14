package com.github.educationissimple.glue.tasks.mappers

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.data.tasks.tuples.NewReminderTuple
import com.github.educationissimple.data.tasks.tuples.RemindersAndTasksTuple
import com.github.educationissimple.notifications.enities.ReminderItem
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun RemindersAndTasksTuple.mapToTaskReminder(): TaskReminder {
    return TaskReminder(
        id = reminder.id,
        taskId = reminder.taskId,
        datetime = reminder.datetime,
        taskText = task.text
    )
}

fun Flow<ResultContainer<List<RemindersAndTasksTuple>>>.mapToTaskReminder(): Flow<ResultContainer<List<TaskReminder>>> {
    return this.map { container ->
        container.map { list ->
            list.map { it.mapToTaskReminder() }
        }
    }
}

fun TaskReminder.toNewReminderTuple(): NewReminderTuple {
    return NewReminderTuple(
        taskId = taskId,
        datetime = datetime
    )
}

fun TaskReminder.toReminderItem(): ReminderItem {
    return ReminderItem(
        id = id,
        text = taskText,
        datetime = datetime
    )
}