package com.github.educationissimple.audio.sources

import com.github.educationissimple.audio.entities.AudioDataEntity

interface AudioDataSource {

    suspend fun getAudio(): List<AudioDataEntity>

    suspend fun addAudio(audio: AudioDataEntity)

    suspend fun deleteAudio(id: Long)
}