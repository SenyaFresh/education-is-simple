package com.github.educationissimple.audio_player.di

import android.content.Context
import androidx.media3.session.MediaSession
import com.github.educationissimple.audio_player.handlers.AudioListPlayerHandler
import com.github.educationissimple.audio_player.notifications.AudioNotification
import com.github.educationissimple.audio_player.services.AudioServiceManager
import com.github.educationissimple.common.di.Service
import dagger.BindsInstance
import dagger.Component

@Service
@Component(modules = [PlayerModule::class])
interface PlayerComponent {

    fun audioServiceManager(): AudioServiceManager

    fun playerListHandler(): AudioListPlayerHandler

    fun mediaSession(): MediaSession

    fun notification(): AudioNotification

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun deps(deps: PlayerDeps): Builder

        fun build(): PlayerComponent
    }
}

interface PlayerDeps {
    val context: Context
}