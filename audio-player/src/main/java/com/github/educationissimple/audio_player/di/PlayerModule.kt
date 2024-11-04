package com.github.educationissimple.audio_player.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import com.github.educationissimple.audio_player.notifications.AudioNotification
import com.github.educationissimple.audio_player.notifications.AudioNotificationImpl
import com.github.educationissimple.common.di.Player
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PlayerModule {

    @Provides
    @Player
    fun provideContext(deps: PlayerDeps): Context = deps.context

    @Provides
    @Player
    fun provideAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
        .setUsage(C.USAGE_MEDIA)
        .build()

    @Provides
    @Player
    @UnstableApi
    fun provideExoPlayer(
        context: Context,
        audioAttributes: AudioAttributes,
    ): ExoPlayer = ExoPlayer.Builder(context)
        .setAudioAttributes(audioAttributes, true)
        .setHandleAudioBecomingNoisy(true)
        .setTrackSelector(DefaultTrackSelector(context))
        .build()

    @Provides
    @Player
    fun provideMediaSession(
        context: Context,
        player: ExoPlayer,
    ): MediaSession = MediaSession.Builder(context, player).build()

    @Binds
    @Player
    abstract fun provideNotificationManager(audioNotificationImpl: AudioNotificationImpl): AudioNotification
}