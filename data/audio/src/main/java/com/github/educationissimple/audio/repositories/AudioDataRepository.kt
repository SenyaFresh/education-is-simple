package com.github.educationissimple.audio.repositories

import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio.tuples.NewAudioCategoryTuple
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

/**
 * The [AudioDataRepository] interface provides the necessary methods to interact with the audio and category data in the repository layer.
 * It includes methods for fetching, adding, updating, and deleting audio items as well as managing categories of audio.
 */
interface AudioDataRepository {

    /**
     * Fetches a list of all audio items.
     *
     * @return A [Flow] emitting a [ResultContainer] wrapping a list of [AudioDataEntity] objects.
     */
    suspend fun getAudio(): Flow<ResultContainer<List<AudioDataEntity>>>

    /**
     * Reloads the audio data. This method is used to refresh the audio data in the repository.
     */
    suspend fun reloadAudio()

    /**
     * Adds a new audio item to the repository.
     *
     * @param audio The [AudioDataEntity] object representing the new audio item to be added.
     */
    suspend fun addAudio(audio: AudioDataEntity)

    /**
     * Deletes an audio item from the repository.
     *
     * @param uri The URI of the audio item to be deleted.
     */
    suspend fun deleteAudio(uri: String)

    /**
     * Fetches the ID of the currently selected audio category.
     *
     * @return A [Flow] emitting a [ResultContainer] wrapping the category ID as a nullable [Long].
     * The result will contain the category ID or null value if no category is selected.
     */
    suspend fun getSelectedCategoryId() : Flow<ResultContainer<Long?>>

    /**
     * Changes the currently selected audio category.
     *
     * @param categoryId The ID of the category to be selected.
     */
    suspend fun changeCategory(categoryId: Long?)

    /**
     * Fetches all audio categories.
     *
     * @return A [Flow] emitting a [ResultContainer] wrapping a list of [AudioCategoryDataEntity] objects.
     */
    suspend fun getCategories(): Flow<ResultContainer<List<AudioCategoryDataEntity>>>

    /**
     * Reloads the audio categories. This method is used to refresh the audio categories in the repository.
     */
    suspend fun reloadCategories()

    /**
     * Creates a new audio category.
     *
     * @param newAudioCategoryTuple The [NewAudioCategoryTuple] object representing the new category to be created.
     */
    suspend fun createCategory(newAudioCategoryTuple: NewAudioCategoryTuple)

    /**
     * Deletes an audio category.
     *
     * @param id The ID of the category to be deleted.
     */
    suspend fun deleteCategory(id: Long)

}