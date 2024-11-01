package com.github.educationissimple.audio.domain.usecases.data

import com.github.educationissimple.audio.domain.repositories.AudioRepository
import javax.inject.Inject

class DeleteAudioUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    suspend fun deleteAudioItem(id: Long) {
        audioRepository.deleteAudioItem(id)
    }

}