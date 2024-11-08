package com.github.educationissimple.audio.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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