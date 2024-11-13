package com.github.educationissimple.glue.tasks.mappers

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.data.tasks.entities.TaskDataEntity
import com.github.educationissimple.tasks.domain.entities.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

fun TaskDataEntity.mapToTask(): Task {
    return Task(
        id = id,
        text = text,
        isCompleted = isCompleted,
        categoryId = categoryId,
        priority = Task.Priority.fromValue(priority),
        date = date,
        description = description
    )
}

fun Task.mapToTaskDataEntity(): TaskDataEntity {
    return TaskDataEntity(
        id = id,
        text = text,
        isCompleted = isCompleted,
        categoryId = categoryId,
        priority = priority.value,
        date = date ?: LocalDate.now(),
        description = description
    )
}

fun Flow<ResultContainer<List<TaskDataEntity>>>.mapToTask(): Flow<ResultContainer<List<Task>>> {
    return this.map { container ->
        container.map { list ->
            list.map { it.mapToTask() }
        }
    }
}