package com.github.educationissimple.audio.sources.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.educationissimple.audio.converters.BitmapConverter
import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.entities.AudioDataEntity

/**
 * The [AudioDatabase] class serves as the main database configuration for the Room database.
 * It also uses a [BitmapConverter] to handle `Bitmap` data types.
 *
 * @see AudioDataEntity
 * @see AudioCategoryDataEntity
 */
@Database(
    version = 1,
    entities = [AudioDataEntity::class, AudioCategoryDataEntity::class]
)
@TypeConverters(BitmapConverter::class)
abstract class AudioDatabase: RoomDatabase() {

    /**
     * Provides access to the [AudioDao], which allows managing audio items in the database.
     *
     * @return An instance of [AudioDao].
     */
    abstract fun getAudioDao(): AudioDao

    /**
     * Provides access to the [AudioCategoryDao], which allows managing audio categories in the database.
     *
     * @return An instance of [AudioCategoryDao].
     */
    abstract fun getAudioCategoryDao(): AudioCategoryDao

}