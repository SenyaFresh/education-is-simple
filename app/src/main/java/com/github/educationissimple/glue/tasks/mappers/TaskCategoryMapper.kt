package com.github.educationissimple.glue.tasks.mappers

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun TaskCategoryDataEntity.mapToTaskCategory(): TaskCategory {
    return TaskCategory(
        id = id,
        name = name
    )
}

fun Flow<ResultContainer<List<TaskCategoryDataEntity>>>.mapToTaskCategory(): Flow<ResultContainer<List<TaskCategory>>> {
    return this.map { container ->
        container.map { list ->
            list.map { it.mapToTaskCategory() }
        }
    }
}