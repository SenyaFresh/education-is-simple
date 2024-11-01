package com.github.educationissimple.audio.domain.handlers

interface PlayerHandler {

    suspend fun getPlayerState() // todo: player state

    suspend fun selectMedia(id: Long)

    suspend fun setPosition(position: Float)

    suspend fun playPause()

    suspend fun close()

    suspend fun next()

    suspend fun previous()

}