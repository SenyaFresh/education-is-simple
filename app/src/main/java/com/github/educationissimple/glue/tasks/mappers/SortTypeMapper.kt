package com.github.educationissimple.glue.tasks.mappers

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Extension function to map a [Flow] of [ResultContainer] of [String] to a [Flow] of [ResultContainer] of [SortType].
 */
fun Flow<ResultContainer<String?>>.mapToSortType(): Flow<ResultContainer<SortType?>> {
    return this.map { container ->
        container.map { string ->
            SortType.fromString(string)

        }
    }
}