package com.github.educationissimple.audio.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.educationissimple.audio.entities.AudioDataEntity

@Dao
interface AudioDao {

    @Query("SELECT * FROM audioItems WHERE (:categoryId IS NULL OR category_id = :categoryId)")
    suspend fun getAudio(categoryId: Long?): List<AudioDataEntity>

    @Insert(entity = AudioDataEntity::class)
    suspend fun addAudio(audioDataEntity: AudioDataEntity)

    @Query("DELETE FROM audioItems WHERE uri = :uri")
    suspend fun deleteAudio(uri: String)

}