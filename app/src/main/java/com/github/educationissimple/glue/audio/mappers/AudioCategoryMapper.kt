package com.github.educationissimple.glue.audio.mappers

import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Maps an [AudioCategoryDataEntity] object to an [AudioCategory] object.
 *
 * @return The mapped [AudioCategory] object.
 */
fun AudioCategoryDataEntity.mapToAudioCategory(): AudioCategory {
    return AudioCategory(
        id = id,
        name = name
    )
}

/**
 * Maps a [Flow] of [ResultContainer] of [List] of [AudioCategoryDataEntity] to a [Flow] of [ResultContainer] of [List] of [AudioCategory].
 *
 * @return A [Flow] of [ResultContainer] of [List] of [AudioCategory].
 */
fun Flow<ResultContainer<List<AudioCategoryDataEntity>>>.mapToAudioCategory(): Flow<ResultContainer<List<AudioCategory>>> {
    return this.map { container ->
        container.map { list ->
            list.map { it.mapToAudioCategory() }
        }
    }
}