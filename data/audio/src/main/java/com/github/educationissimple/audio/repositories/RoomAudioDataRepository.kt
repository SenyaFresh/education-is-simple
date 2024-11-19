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

/**
 * The [RoomAudioDataRepository] class implements the [AudioDataRepository] interface for managing audio data and categories
 * using Room as the underlying data source. It integrates with the [AudioDataSource] and [AudioPreferencesDataSource] to handle
 * audio data operations and category management while leveraging [LazyFlowLoaderFactory] to provide lazy-loaded data streams.
 *
 * @param audioDataSource The [AudioDataSource] responsible for fetching and managing audio data.
 * @param audioPreferencesDataSource The [AudioPreferencesDataSource] responsible for managing user preferences related to audio.
 * @param lazyFlowLoaderFactory The [LazyFlowLoaderFactory] used to create lazy-loaded data streams.
 */
class RoomAudioDataRepository @Inject constructor(
    private val audioDataSource: AudioDataSource,
    private val audioPreferencesDataSource: AudioPreferencesDataSource,
    lazyFlowLoaderFactory: LazyFlowLoaderFactory
) : AudioDataRepository {

    /**
     * The currently selected audio category ID.
     */
    private var currentCategoryId: Long?
        get() = audioPreferencesDataSource.getSelectedCategoryId()
        set(value) = audioPreferencesDataSource.saveSelectedCategoryId(value)

    /**
     * The lazy-loaded data stream for fetching audio data.
     */
    private val audioLoader = lazyFlowLoaderFactory.create {
        audioDataSource.getAudio(currentCategoryId)
    }

    /**
     * The lazy-loaded data stream for fetching audio categories.
     */
    private val categoriesLoader = lazyFlowLoaderFactory.create {
        audioDataSource.getCategories()
    }

    /**
     * The lazy-loaded data stream for fetching the currently selected audio category ID.
     */
    private val currentCategoryIdLoader = lazyFlowLoaderFactory.create {
        currentCategoryId
    }

    /**
     * Fetches a list of audio items from the repository, based on the current selected category.
     *
     * @return A [Flow] emitting a [ResultContainer] wrapping a list of [AudioDataEntity] objects.
     */
    override suspend fun getAudio(): Flow<ResultContainer<List<AudioDataEntity>>> {
        return audioLoader.listen()
    }

    /**
     * Reloads the audio data by forcing a refresh of the audio loader.
     */
    override suspend fun reloadAudio() {
        audioLoader.newAsyncLoad(silently = false)
    }

    /**
     * Adds a new audio item to the repository.
     *
     * @param audio The [AudioDataEntity] object representing the audio item to be added.
     */
    override suspend fun addAudio(audio: AudioDataEntity) {
        audioDataSource.addAudio(audio)
        updateSources()
    }

    /**
     * Deletes an audio item from the repository using its URI.
     *
     * @param uri The URI of the audio item to be deleted.
     */
    override suspend fun deleteAudio(uri: String) {
        audioDataSource.deleteAudio(uri)
        updateSources()
    }

    /**
     * Fetches the ID of the currently selected audio category.
     *
     * @return A [Flow] emitting a [ResultContainer] wrapping the current category ID, or `null` if no category is selected.
     */
    override suspend fun getSelectedCategoryId(): Flow<ResultContainer<Long?>> {
        return currentCategoryIdLoader.listen()
    }

    /**
     * Changes the currently selected audio category.
     *
     * @param categoryId The ID of the category to be selected.
     */
    override suspend fun changeCategory(categoryId: Long?) {
        currentCategoryId = categoryId
        currentCategoryIdLoader.newAsyncLoad(silently = true)
        updateSources()
    }

    /**
     * Fetches all audio categories from the repository.
     *
     * @return A [Flow] emitting a [ResultContainer] wrapping a list of [AudioCategoryDataEntity] objects.
     */
    override suspend fun getCategories(): Flow<ResultContainer<List<AudioCategoryDataEntity>>> {
        return categoriesLoader.listen()
    }

    /**
     * Reloads the categories data by forcing a refresh of the category loader.
     */
    override suspend fun reloadCategories() {
        categoriesLoader.newAsyncLoad(silently = false)
    }

    /**
     * Creates a new audio category in the repository.
     *
     * @param newAudioCategoryTuple A [NewAudioCategoryTuple] containing the details of the new category to be created.
     */
    override suspend fun createCategory(newAudioCategoryTuple: NewAudioCategoryTuple) {
        audioDataSource.createCategory(newAudioCategoryTuple)
        categoriesLoader.newAsyncLoad(silently = true)
    }

    /**
     * Deletes an audio category from the repository by its ID.
     *
     * @param id The ID of the audio category to be deleted.
     */
    override suspend fun deleteCategory(id: Long) {
        audioDataSource.deleteCategory(id)
        if (currentCategoryId == id) {
            currentCategoryId = null
        }
        categoriesLoader.newAsyncLoad(silently = true)
        updateSources()
    }

    /**
     * Updates the sources by forcing a refresh of the audio data loader.
     *
     * @param silently A flag to indicate whether the data refresh should be done silently.
     */
    private fun updateSources(silently: Boolean = false) {
        audioLoader.newAsyncLoad(
            valueLoader = { audioDataSource.getAudio(currentCategoryId) },
            silently = silently
        )
    }
}