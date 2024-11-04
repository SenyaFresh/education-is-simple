package com.github.educationissimple.audio.sources.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.educationissimple.audio.entities.AudioDataEntity

@Dao
interface AudioDao {

    @Query("SELECT * FROM audioItems")
    suspend fun getAudio(): List<AudioDataEntity>

    @Insert(entity = AudioDataEntity::class)
    suspend fun addAudio(audioDataEntity: AudioDataEntity)

    @Query("DELETE FROM audioItems WHERE id = :id")
    suspend fun deleteAudio(id: Long)

}