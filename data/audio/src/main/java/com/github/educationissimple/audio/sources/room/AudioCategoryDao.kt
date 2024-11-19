package com.github.educationissimple.audio.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.tuples.NewAudioCategoryTuple

/**
 * The [Dao] interface provides data access operations for managing
 * audio categories in the Room database. This DAO allows querying, inserting, and deleting
 * categories stored in the `audio_categories` table.
 */
@Dao
interface AudioCategoryDao {

    /**
     * Retrieves all audio categories from the database.
     *
     * @return A list of [AudioCategoryDataEntity] objects representing the audio categories.
     */
    @Query("SELECT * FROM audio_categories")
    suspend fun getCategories(): List<AudioCategoryDataEntity>

    /**
     * Inserts a new audio category into the database.
     *
     * @param newTaskCategoryTuple The [NewAudioCategoryTuple] object containing the details of the new category.
     */
    @Insert(entity = AudioCategoryDataEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun createCategory(newTaskCategoryTuple: NewAudioCategoryTuple)

    /**
     * Deletes an audio category from the database by its ID.
     *
     * @param id The ID of the category to delete.
     */
    @Query("DELETE FROM audio_categories WHERE id = :id")
    suspend fun deleteCategory(id: Long)

}