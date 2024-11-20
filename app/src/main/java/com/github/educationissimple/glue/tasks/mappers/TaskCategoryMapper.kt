package com.github.educationissimple.glue.tasks.mappers

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.data.tasks.entities.TaskCategoryDataEntity
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Extension function to map a [TaskCategoryDataEntity] to a [TaskCategory].
 */
fun TaskCategoryDataEntity.mapToTaskCategory(): TaskCategory {
    return TaskCategory(
        id = id,
        name = name
    )
}

/**
 * Extension function to map a [Flow] of [ResultContainer] of [TaskCategoryDataEntity] to a [Flow] of [ResultContainer] of [TaskCategory].
 */
fun Flow<ResultContainer<List<TaskCategoryDataEntity>>>.mapToTaskCategory(): Flow<ResultContainer<List<TaskCategory>>> {
    return this.map { container ->
        container.map { list ->
            list.map { it.mapToTaskCategory() }
        }
    }
}