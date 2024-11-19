package com.github.educationissimple.audio.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents an audio item entity for storing in the Room Database.
 * This entity manages audio items and their data.
 *
 * @param uri The unique URI of the audio item. This acts as the primary key of the entity.
 * @param categoryId The ID of the category to which the audio item belongs. It is a foreign key linking to the [AudioCategoryDataEntity] table.
 * @param imageBitmap The image associated with the audio item (e.g., album artwork), stored as a Bitmap object.
 * @param title The title of the audio item (e.g., song name or podcast episode title).
 * @param subtitle A short description or subtitle for the audio item (e.g., artist name or podcast series name).
 * @param duration The duration of the audio item in milliseconds.
 */
@Entity(
    tableName = "audioItems",
    foreignKeys = [
        ForeignKey(
            entity = AudioCategoryDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["category_id"])]
)
data class AudioDataEntity(
    @PrimaryKey val uri: String,
    @ColumnInfo(name = "category_id") val categoryId: Long?,
    @ColumnInfo(name = "image_uri") val imageBitmap: Bitmap?,
    val title: String?,
    val subtitle: String?,
    val duration: Long?
)