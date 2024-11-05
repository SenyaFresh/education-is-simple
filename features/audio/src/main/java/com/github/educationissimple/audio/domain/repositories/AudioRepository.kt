package com.github.educationissimple.audio.domain.repositories

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

interface AudioRepository {

    suspend fun getAudioItems(): Flow<ResultContainer<List<Audio>>>

    suspend fun addAudioItem(uri: String)

    suspend fun deleteAudioItem(uri: String)

}