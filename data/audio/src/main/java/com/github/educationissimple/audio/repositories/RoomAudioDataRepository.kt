package com.github.educationissimple.audio.repositories

import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio.sources.AudioDataSource
import com.github.educationissimple.audio.sources.AudioPreferencesDataSource
import com.github.educationissimple.audio.tuples.NewAudioCategoryTuple
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.common.flow.LazyFlowLoaderFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomAudioDataRepository @Inject constructor(
    private val audioDataSource: AudioDataSource,
    private val audioPreferencesDataSource: AudioPreferencesDataSource,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : AudioDataRepository {

    private var currentCategoryId: Long?
        get() = audioPreferencesDataSource.getSelectedCategoryId()
        set(value) = audioPreferencesDataSource.saveSelectedCategoryId(value)

    private val audioLoader = lazyFlowLoaderFactory.create {
        audioDataSource.getAudio(currentCategoryId)
    }

    private val categoriesLoader = lazyFlowLoaderFactory.create {
        audioDataSource.getCategories()
    }

    private val currentCategoryIdLoader = lazyFlowLoaderFactory.create {
        currentCategoryId
    }

    override suspend fun getAudio(): Flow<ResultContainer<List<AudioDataEntity>>> {
        return audioLoader.listen()
    }

    override suspend fun addAudio(audio: AudioDataEntity) {
        audioDataSource.addAudio(audio)
        updateSources()
    }

    override suspend fun deleteAudio(uri: String) {
        audioDataSource.deleteAudio(uri)
        updateSources()
    }

    override suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>> {
        return currentCategoryIdLoader.listen()
    }

    override suspend fun changeCategory(categoryId: Long?) {
        currentCategoryId = categoryId
        currentCategoryIdLoader.newAsyncLoad(silently = true)
        updateSources()
    }

    override suspend fun getCategories(): Flow<ResultContainer<List<AudioCategoryDataEntity>>> {
        return categoriesLoader.listen()
    }

    override suspend fun createCategory(newAudioCategoryTuple: NewAudioCategoryTuple) {
        audioDataSource.createCategory(newAudioCategoryTuple)
        categoriesLoader.newAsyncLoad(silently = true)
    }

    override suspend fun deleteCategory(id: Long) {
        audioDataSource.deleteCategory(id)
        if (currentCategoryId == id) {
            currentCategoryId = null
        }
        categoriesLoader.newAsyncLoad(silently = true)
        updateSources()
    }

    private fun updateSources(silently: Boolean = false) {
        audioLoader.newAsyncLoad(
            valueLoader = { audioDataSource.getAudio(currentCategoryId) },
            silently = silently
        )
    }
}