package com.github.educationissimple.audio.domain.repositories

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.common.ResultContainer
import kotlinx.coroutines.flow.Flow

/**
 * An interface that defines operations for managing audio items and categories.
 *
 * This interface provides methods for retrieving, adding, deleting, and modifying audio items and categories.
 * It also allows for managing the selected category and reloading audio data.
 */
interface AudioRepository {

    /**
     * Retrieves a list of audio items.
     *
     * @return A [Flow] that emits a [ResultContainer] of a list of [Audio] items.
     */
    suspend fun getAudioItems(): Flow<ResultContainer<List<Audio>>>

    /**
     * Reloads the audio items.
     */
    suspend fun reloadAudioItems()

    /**
     * Adds a new audio item.
     *
     * @param uri The URI of the audio item.
     * @param categoryId The ID of the category the audio item should belong to, or null if no category.
     */
    suspend fun addAudioItem(uri: String, categoryId: Long?)

    /**
     * Deletes an audio item.
     *
     * This method deletes the audio item with the specified URI.
     *
     * @param uri The URI of the audio item to be deleted.
     */
    suspend fun deleteAudioItem(uri: String)

    /**
     * Retrieves a list of audio categories.
     *
     * @return A [Flow] that emits a [ResultContainer] of a list of [AudioCategory] items.
     */
    suspend fun getCategories(): Flow<ResultContainer<List<AudioCategory>>>

    /**
     * Reloads the audio categories.
     */
    suspend fun reloadCategories()

    /**
     * Creates a new audio category.
     *
     * @param name The name of the new audio category.
     */
    suspend fun createCategory(name: String)

    /**
     * Deletes an audio category.
     *
     * @param categoryId The ID of the category to be deleted.
     */
    suspend fun deleteCategory(categoryId: Long)

    /**
     * Changes the selected category.
     *
     * @param categoryId The ID of the category to be selected, or null to deselect.
     */
    suspend fun changeCategory(categoryId: Long?)

    /**
     * Retrieves the currently selected category ID.
     *
     * @return A [Flow] that emits a [ResultContainer] with the selected category ID, or null if no category is selected.
     */
    suspend fun getSelectedCategoryId() : Flow<ResultContainer<Long?>>

}