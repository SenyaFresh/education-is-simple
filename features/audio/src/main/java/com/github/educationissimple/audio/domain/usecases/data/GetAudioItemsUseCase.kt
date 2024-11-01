package com.github.educationissimple.audio.domain.usecases.data

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAudioItemsUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    suspend fun getAudioItems(): Flow<List<Audio>> {
        return audioRepository.getAudioItems()
    }

}