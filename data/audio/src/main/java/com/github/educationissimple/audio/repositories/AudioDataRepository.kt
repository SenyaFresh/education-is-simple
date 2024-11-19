package com.github.educationissimple.audio.repositories

import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio.tuples.NewAudioCategoryTuple
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

interface AudioDataRepository {

    suspend fun getAudio(): Flow<ResultContainer<List<AudioDataEntity>>>

    suspend fun reloadAudio()

    suspend fun addAudio(audio: AudioDataEntity)

    suspend fun deleteAudio(uri: String)

    suspend fun getSelectedCategoryId() : Flow<ResultContainer<Long?>>

    suspend fun changeCategory(categoryId: Long?)

    suspend fun getCategories(): Flow<ResultContainer<List<AudioCategoryDataEntity>>>

    suspend fun reloadCategories()

    suspend fun createCategory(newAudioCategoryTuple: NewAudioCategoryTuple)

    suspend fun deleteCategory(id: Long)

}