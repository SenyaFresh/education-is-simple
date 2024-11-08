package com.github.educationissimple.audio.domain.usecases.data

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAudioItemsUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    suspend fun getAudioItems(): Flow<ResultContainer<List<Audio>>> {
        return audioRepository.getAudioItems()
    }

    suspend fun changeSelectedCategoryId(categoryId: Long?) {
        audioRepository.changeCategory(categoryId)
    }

}