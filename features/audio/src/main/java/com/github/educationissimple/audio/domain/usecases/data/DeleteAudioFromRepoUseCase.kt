package com.github.educationissimple.audio.domain.usecases.data

import com.github.educationissimple.audio.domain.repositories.AudioRepository
import javax.inject.Inject

/**
 * Use case for deleting an audio item from the repository.
 */
class DeleteAudioFromRepoUseCase @Inject constructor(private val audioRepository: AudioRepository) {

    /**
     * Deletes an audio item.
     *
     * @param uri The URI of the audio item to be deleted.
     */
    suspend fun deleteAudioItem(uri: String) {
        audioRepository.deleteAudioItem(uri)
    }

}