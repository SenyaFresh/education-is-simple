package com.github.educationissimple.audio.domain.usecases.data

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting audio items.
 */
class GetAudioItemsUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    /**
     * Retrieves a list of audio items.
     *
     * @return A [Flow] that emits a [ResultContainer] of a list of [Audio] items.
     */
    suspend fun getAudioItems(): Flow<ResultContainer<List<Audio>>> {
        return audioRepository.getAudioItems()
    }

    /**
     * Reloads the audio items.
     */
    suspend fun reloadAudioItems() {
        audioRepository.reloadAudioItems()
    }

    /**
     * Changes the selected category.
     *
     * @param categoryId The ID of the category to be selected, or null to deselect.
     */
    suspend fun changeSelectedCategoryId(categoryId: Long?) {
        audioRepository.changeCategory(categoryId)
    }

}