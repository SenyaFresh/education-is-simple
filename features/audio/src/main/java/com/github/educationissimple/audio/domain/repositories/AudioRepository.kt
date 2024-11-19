package com.github.educationissimple.audio.domain.repositories

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

interface AudioRepository {

    suspend fun getAudioItems(): Flow<ResultContainer<List<Audio>>>

    suspend fun reloadAudioItems()

    suspend fun addAudioItem(uri: String, categoryId: Long?)

    suspend fun deleteAudioItem(uri: String)

    suspend fun getCategories(): Flow<ResultContainer<List<AudioCategory>>>

    suspend fun reloadCategories()

    suspend fun createCategory(name: String)

    suspend fun deleteCategory(categoryId: Long)

    suspend fun changeCategory(categoryId: Long?)

    suspend fun getSelectedCategoryId() : Flow<ResultContainer<Long?>>

}