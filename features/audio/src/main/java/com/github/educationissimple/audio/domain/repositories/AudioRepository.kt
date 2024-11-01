package com.github.educationissimple.audio.domain.repositories

import com.github.educationissimple.audio.domain.entities.Audio
import kotlinx.coroutines.flow.Flow

interface AudioRepository {

    suspend fun getAudioItems(): Flow<List<Audio>>

    suspend fun addAudioItem(audio: Audio)

    suspend fun deleteAudioItem(id: Long)

}