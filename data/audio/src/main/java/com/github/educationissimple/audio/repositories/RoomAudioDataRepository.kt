package com.github.educationissimple.audio.repositories

import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio.sources.AudioDataSource
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.common.flow.LazyFlowLoaderFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomAudioDataRepository @Inject constructor(
    private val audioDataSource: AudioDataSource,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
): AudioDataRepository {
    private val audioLoader = lazyFlowLoaderFactory.create {
        audioDataSource.getAudio()
    }

    override suspend fun getAudio(): Flow<ResultContainer<List<AudioDataEntity>>> {
        return audioLoader.listen()
    }

    override suspend fun addAudio(audio: AudioDataEntity) {
        audioDataSource.addAudio(audio)
        audioLoader.newAsyncLoad(silently = true)
    }

    override suspend fun deleteAudio(uri: String) {
        audioDataSource.deleteAudio(uri)
        audioLoader.newAsyncLoad(silently = true)
    }
}