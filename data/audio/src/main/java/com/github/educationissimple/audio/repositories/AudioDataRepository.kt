package com.github.educationissimple.audio.repositories

import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

interface AudioDataRepository {

    suspend fun getAudio(): Flow<ResultContainer<List<AudioDataEntity>>>

    suspend fun addAudio(audio: AudioDataEntity)

    suspend fun deleteAudio(uri: String)

}