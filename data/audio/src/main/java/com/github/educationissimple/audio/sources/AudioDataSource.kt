package com.github.educationissimple.audio.sources

import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio.tuples.NewAudioCategoryTuple

interface AudioDataSource {

    suspend fun getAudio(categoryId: Long?): List<AudioDataEntity>

    suspend fun addAudio(audio: AudioDataEntity)

    suspend fun deleteAudio(uri: String)

    suspend fun getCategories(): List<AudioCategoryDataEntity>

    suspend fun createCategory(newAudioCategoryTuple: NewAudioCategoryTuple)

    suspend fun deleteCategory(id: Long)

}