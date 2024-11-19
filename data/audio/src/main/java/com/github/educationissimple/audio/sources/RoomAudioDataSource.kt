package com.github.educationissimple.audio.sources

import android.content.Context
import androidx.room.Room
import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio.sources.room.AudioDatabase
import com.github.educationissimple.audio.tuples.NewAudioCategoryTuple
import javax.inject.Inject

/**
 * Implementation of the [AudioDataSource] interface using Room as the database.
 *
 * This class provides methods to manage audio items and categories using a Room
 * database. It supports CRUD operations and initializes the database from an
 * asset if specified.
 *
 * @param context The application context used for database initialization.
 */
class RoomAudioDataSource @Inject constructor(
    context: Context
) : AudioDataSource {

    /**
     * Lazily initialized instance of the `AudioDatabase` using Room.
     * The database is created with an initial state from an asset file.
     */
    private val db: AudioDatabase by lazy<AudioDatabase> {
        Room.databaseBuilder(
            context,
            AudioDatabase::class.java,
            "audio.db"
        )
            .createFromAsset("initial_audio_database.db")
            .build()
    }

    /**
     * Data Access Object for audio items.
     */
    private val audioDao = db.getAudioDao()

    /**
     * Data Access Object for audio categories.
     */
    private val audioCategoryDao = db.getAudioCategoryDao()

    /**
     * Fetches a list of audio items filtered by category ID.
     *
     * If the category ID is `null`, all audio items are retrieved.
     *
     * @param categoryId The ID of the category to filter by, or `null` for all items.
     * @return A list of [AudioDataEntity] objects matching the criteria.
     */
    override suspend fun getAudio(categoryId: Long?): List<AudioDataEntity> {
        return audioDao.getAudio(categoryId)
    }

    /**
     * Adds a new audio item to the database.
     *
     * @param audio The [AudioDataEntity] object to be added.
     */
    override suspend fun addAudio(audio: AudioDataEntity) {
        audioDao.addAudio(audio)
    }

    /**
     * Deletes an audio item from the database based on its URI.
     *
     * @param uri The URI of the audio item to delete.
     */
    override suspend fun deleteAudio(uri: String) {
        audioDao.deleteAudio(uri)
    }

    /**
     * Retrieves a list of all audio categories from the database.
     *
     * @return A list of [AudioCategoryDataEntity] objects representing the categories.
     */
    override suspend fun getCategories(): List<AudioCategoryDataEntity> {
        return audioCategoryDao.getCategories()
    }

    /**
     * Creates a new audio category in the database.
     *
     * @param newAudioCategoryTuple The [NewAudioCategoryTuple] object containing the details of the new category.
     */
    override suspend fun createCategory(newAudioCategoryTuple: NewAudioCategoryTuple) {
        return audioCategoryDao.createCategory(newAudioCategoryTuple)
    }

    /**
     * Deletes an audio category from the database based on its ID.
     *
     * @param id The ID of the category to delete.
     */
    override suspend fun deleteCategory(id: Long) {
        audioCategoryDao.deleteCategory(id)
    }
}