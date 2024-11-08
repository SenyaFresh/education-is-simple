package com.github.educationissimple.audio.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.tuples.NewAudioCategoryTuple

@Dao
interface AudioCategoryDao {

    @Query("SELECT * FROM audio_categories")
    suspend fun getCategories(): List<AudioCategoryDataEntity>

    @Insert(entity = AudioCategoryDataEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun createCategory(newTaskCategoryTuple: NewAudioCategoryTuple)

    @Query("DELETE FROM audio_categories WHERE id = :id")
    suspend fun deleteCategory(id: Long)

}