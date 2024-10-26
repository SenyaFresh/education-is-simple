package com.github.educationissimple.glue.tasks.mappers

import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.tasks.domain.entities.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<ResultContainer<String?>>.mapToSortType(): Flow<ResultContainer<SortType?>> {
    return this.map { container ->
        container.map { string ->
            SortType.fromString(string)

        }
    }
}