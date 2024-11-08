package com.github.educationissimple.audio.domain.usecases.categories

import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    suspend fun getCategories(): Flow<ResultContainer<List<AudioCategory>>> {
        return audioRepository.getCategories()
    }

    suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>> {
        return audioRepository.getSelectedCategoryId()
    }
}