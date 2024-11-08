package com.github.educationissimple.glue.audio.mappers

import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun AudioCategoryDataEntity.mapToAudioCategory(): AudioCategory {
    return AudioCategory(
        id = id,
        name = name
    )
}

fun Flow<ResultContainer<List<AudioCategoryDataEntity>>>.mapToAudioCategory(): Flow<ResultContainer<List<AudioCategory>>> {
    return this.map { container ->
        container.map { list ->
            list.map { it.mapToAudioCategory() }
        }
    }
}