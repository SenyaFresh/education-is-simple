package com.github.educationissimple.audio.domain.usecases.data

import com.github.educationissimple.audio.domain.repositories.AudioRepository
import javax.inject.Inject

class AddAudioToRepoUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    suspend fun addAudioItem(uri: String) {
        audioRepository.addAudioItem(uri)
    }

}