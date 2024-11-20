package com.github.educationissimple.glue.audio.mappers

import android.app.Application
import android.content.ContentUris
import android.graphics.Bitmap
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Size
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio_player.entities.AudioItem
import com.github.educationissimple.audio_player.entities.AudioPlayerListState

/**
 * Maps an [AudioDataEntity] object to an [Audio] object.
 *
 * @return The mapped [Audio] object.
 */
fun AudioDataEntity.toAudio(): Audio {
    return Audio(
        uri = uri,
        categoryId = null,
        imageBitmap = imageBitmap,
        title = title ?: "Неизвестно",
        subtitle = subtitle ?: "Неизвестно",
        duration = duration ?: 0
    )
}

/**
 * Projection array for querying audio data from the media store.
 * This array contains the columns that will be retrieved from the content provider.
 */
private val projection: Array<String> = arrayOf(
    MediaStore.Audio.AudioColumns.ARTIST,
    MediaStore.Audio.AudioColumns.DURATION,
    MediaStore.Audio.AudioColumns.TITLE,
    MediaStore.Audio.Albums.ALBUM_ID
)

/**
 * Converts a [Uri] to an [AudioDataEntity] object.
 *
 * @param application The application context.
 * @return The converted [AudioDataEntity] object, or null if the conversion fails.
 */
fun Uri.toAudioDataEntity(application: Application): AudioDataEntity? {
    var audioDataEntity: AudioDataEntity? = null

    val mediaUri = if (DocumentsContract.isDocumentUri(application, this)) {
        val docId = DocumentsContract.getDocumentId(this)
        val mediaId = docId.split(":")[1]
        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mediaId.toLong())
    } else {
        this
    }

    application.contentResolver.query(
        mediaUri,
        projection,
        null,
        null,
        null
    )?.use { cursor ->
        if (cursor.moveToFirst()) {
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ID)
            val albumId = cursor.getLong(albumIdColumn)
            val albumBitmap = getAlbumBitmap(application, albumId)

            audioDataEntity = AudioDataEntity(
                uri = mediaUri.toString(),
                imageBitmap = albumBitmap,
                title = cursor.getString(titleColumn),
                subtitle = cursor.getString(artistColumn),
                duration = cursor.getLong(durationColumn),
                categoryId = null
            )
        }
    }

    return audioDataEntity
}

/**
 * Retrieves the album art bitmap for a given album ID.
 *
 * @param application The application context.
 * @param albumId The ID of the album.
 *
 * @return The album art bitmap, or null if the retrieval fails.
 **/
fun getAlbumBitmap(application: Application, albumId: Long): Bitmap? {
    val albumUri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId)

    runCatching {
        return application.contentResolver.loadThumbnail(albumUri, Size(480, 480), null)
    }
    return null
}

/**
 * Maps an [AudioPlayerListState] object to an [AudioListState] object.
 *
 * @return The mapped [AudioListState] object.
 */
fun AudioPlayerListState.toAudioListState(): AudioListState {
    return AudioListState(
        state = state.toAudioPlayerListStateState(),
        currentAudioUri = currentAudioUri,
        positionMs = positionMs,
        durationMs = durationMs
    )
}

/**
 * Maps an [Audio] object to an [AudioItem] object.
 *
 * @return The mapped [AudioItem] object.
 */
fun Audio.toAudioItem(): AudioItem {
    return AudioItem(
        uri = uri,
        categoryId = categoryId,
        imageBitmap = imageBitmap,
        title = title,
        subtitle = subtitle,
        duration = duration
    )
}

/**
 * Maps an [AudioListState.State] object to an [AudioPlayerListState.State] object.
 *
 * @return The mapped [AudioPlayerListState.State] object.
 */
fun AudioPlayerListState.State.toAudioPlayerListStateState(): AudioListState.State {
    return when (this) {
        AudioPlayerListState.State.AUDIO_PLAYING -> AudioListState.State.AUDIO_PLAYING
        AudioPlayerListState.State.BUFFERING -> AudioListState.State.BUFFERING
        AudioPlayerListState.State.READY -> AudioListState.State.READY
        AudioPlayerListState.State.IDLE -> AudioListState.State.IDLE
    }
}