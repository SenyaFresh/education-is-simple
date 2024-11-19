package com.github.educationissimple.audio.sources

import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio.tuples.NewAudioCategoryTuple

/**
 * Data source interface for managing audio data and categories.
 *
 * This interface defines methods for accessing, adding, updating, and deleting
 * audio-related data and categories.
 */
interface AudioDataSource {

    /**
     * Retrieves the list of audio items based on the provided category ID.
     *
     * If the category ID is `null`, this method should return all audio items.
     *
     * @param categoryId The ID of the category to filter audio items, or `null` to retrieve all items.
     * @return A list of `AudioDataEntity` objects matching the specified category.
     */
    suspend fun getAudio(categoryId: Long?): List<AudioDataEntity>

    /**
     * Adds a new audio item to the data source.
     *
     * @param audio The audio data to be added.
     */
    suspend fun addAudio(audio: AudioDataEntity)

    /**
     * Deletes an audio item from the data source based on its URI.
     *
     * @param uri The URI of the audio item to be deleted.
     */
    suspend fun deleteAudio(uri: String)

    /**
     * Retrieves all audio categories available in the data source.
     *
     * @return A list of audio categories.
     */
    suspend fun getCategories(): List<AudioCategoryDataEntity>

    /**
     * Creates a new audio category in the data source.
     *
     * @param newAudioCategoryTuple The details of the new audio category.
     */
    suspend fun createCategory(newAudioCategoryTuple: NewAudioCategoryTuple)

    /**
     * Deletes an audio category from the data source based on its ID.
     *
     * @param id The ID of the audio category to be deleted.
     */
    suspend fun deleteCategory(id: Long)

}