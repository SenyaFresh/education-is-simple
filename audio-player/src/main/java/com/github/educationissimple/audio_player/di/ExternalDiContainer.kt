package com.github.educationissimple.audio_player.di

import com.github.educationissimple.audio_player.handlers.AudioListPlayerHandler
import javax.inject.Inject

class ExternalDiContainer {

    @Inject
    lateinit var playerListHandler: AudioListPlayerHandler

    companion object {
        private var _instance: ExternalDiContainer? = null

        val playerListHandler: AudioListPlayerHandler
            get() = checkNotNull(_instance) { "ExternalDiContainer not initialized" }.playerListHandler
    }

}