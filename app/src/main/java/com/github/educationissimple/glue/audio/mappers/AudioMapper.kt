package com.github.educationissimple.glue.audio.mappers

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio_player.entities.AudioPlayerListState

fun AudioDataEntity.toAudio(): Audio {
    return Audio(
        uri = uri,
        categoryId = null,
        imageUri = imageUri,
        title = title ?: "Неизвестно",
        subtitle = subtitle ?: "Неизвестно",
        duration = duration ?: 0
    )
}


@OptIn(UnstableApi::class)
fun Uri.toAudioDataEntity(): AudioDataEntity {
    val mediaItem = MediaItem.fromUri(this)
    return AudioDataEntity(
        uri = this.toString(),
        imageUri = mediaItem.mediaMetadata.artworkUri.toString(),
        title = mediaItem.mediaMetadata.title.toString(),
        subtitle = mediaItem.mediaMetadata.subtitle.toString(),
        duration = mediaItem.mediaMetadata.durationMs
    )
}

fun AudioPlayerListState.toAudioListState(): AudioListState {
    return AudioListState(
        state = state.toAudioPlayerListStateState(),
        currentAudioUri = currentAudioUri,
        positionMs = positionMs,
        durationMs = durationMs
    )
}

fun AudioPlayerListState.State.toAudioPlayerListStateState(): AudioListState.State {
    return when (this) {
        AudioPlayerListState.State.AUDIO_PLAYING -> AudioListState.State.AUDIO_PLAYING
        AudioPlayerListState.State.BUFFERING -> AudioListState.State.BUFFERING
        AudioPlayerListState.State.PLAYING -> AudioListState.State.PLAYING
        AudioPlayerListState.State.READY -> AudioListState.State.READY
    }
}