package com.github.educationissimple.audio.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.educationissimple.audio.entities.AudioDataEntity

/**
 * The [Dao] interface provides data access operations for managing audio items
 * in the Room database. It enables querying, inserting, and deleting audio items stored
 * in the `audioItems` table.
 */
@Dao
interface AudioDao {

    /**
     * Retrieves audio items from the database filtered by category.
     *
     * @param categoryId The ID of the category to filter by, or `null` to retrieve all items.
     * @return A list of [AudioDataEntity] objects representing the filtered audio items.
     */
    @Query("SELECT * FROM audioItems WHERE (:categoryId IS NULL OR category_id = :categoryId)")
    suspend fun getAudio(categoryId: Long?): List<AudioDataEntity>

    /**
     * Inserts a new audio item into the database.
     *
     * @param audioDataEntity An [AudioDataEntity] object containing the details of the audio item.
     */
    @Insert(entity = AudioDataEntity::class)
    suspend fun addAudio(audioDataEntity: AudioDataEntity)

    /**
     * Deletes an audio item from the database by its unique URI.
     *
     * @param uri The URI of the audio item to delete.
     */
    @Query("DELETE FROM audioItems WHERE uri = :uri")
    suspend fun deleteAudio(uri: String)

}