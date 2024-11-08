package com.github.educationissimple.audio.sources

import android.content.Context
import androidx.room.Room
import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio.sources.room.AudioDatabase
import com.github.educationissimple.audio.tuples.NewAudioCategoryTuple
import javax.inject.Inject

class RoomAudioDataSource @Inject constructor(
    context: Context
) : AudioDataSource {

    private val db: AudioDatabase by lazy<AudioDatabase> {
        Room.databaseBuilder(
            context,
            AudioDatabase::class.java,
            "audio.db"
        )
            .createFromAsset("initial_audio_database.db")
            .build()
    }

    private val audioDao = db.getAudioDao()
    private val audioCategoryDao = db.getAudioCategoryDao()

    override suspend fun getAudio(categoryId: Long?): List<AudioDataEntity> {
        return audioDao.getAudio(categoryId)
    }

    override suspend fun addAudio(audio: AudioDataEntity) {
        audioDao.addAudio(audio)
    }

    override suspend fun deleteAudio(uri: String) {
        audioDao.deleteAudio(uri)
    }

    override suspend fun getCategories(): List<AudioCategoryDataEntity> {
        return audioCategoryDao.getCategories()
    }

    override suspend fun createCategory(newAudioCategoryTuple: NewAudioCategoryTuple) {
        return audioCategoryDao.createCategory(newAudioCategoryTuple)
    }

    override suspend fun deleteCategory(id: Long) {
        audioCategoryDao.deleteCategory(id)
    }
}