package com.github.educationissimple.audio.domain.usecases.categories

import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting categories.
 */
class GetCategoriesUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    /**
     * Retrieves a list of categories.
     *
     * @return A [Flow] emitting a [ResultContainer] containing a list of [AudioCategory] items.
     */
    suspend fun getCategories(): Flow<ResultContainer<List<AudioCategory>>> {
        return audioRepository.getCategories()
    }

    /**
     * Reloads the categories.
     */
    suspend fun reloadCategories() {
        audioRepository.reloadCategories()
    }

    /**
     * Retrieves the currently selected category ID.
     *
     * @return A [Flow] that emits a [ResultContainer] with the selected category ID, or null if no category is selected.
     */
    suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>> {
        return audioRepository.getSelectedCategoryId()
    }
}