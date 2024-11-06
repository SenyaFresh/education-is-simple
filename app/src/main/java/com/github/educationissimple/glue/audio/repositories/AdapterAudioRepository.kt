package com.github.educationissimple.glue.audio.repositories

import android.app.Application
import android.net.Uri
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.audio.repositories.AudioDataRepository
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.glue.audio.mappers.toAudio
import com.github.educationissimple.glue.audio.mappers.toAudioDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterAudioRepository @Inject constructor(
    private val audioDataRepository: AudioDataRepository,
    private val application: Application
) : AudioRepository {

    override suspend fun getAudioItems(): Flow<ResultContainer<List<Audio>>> {
        return audioDataRepository.getAudio().map { container -> container.map { list -> list.map { it.toAudio() } } }
    }

    override suspend fun addAudioItem(uri: String) {
        val audio = Uri.parse(uri).toAudioDataEntity(application)
        audioDataRepository.addAudio(audio ?: throw Exception()) // todo
    }

    override suspend fun deleteAudioItem(uri: String) {
        audioDataRepository.deleteAudio(uri)
    }

}