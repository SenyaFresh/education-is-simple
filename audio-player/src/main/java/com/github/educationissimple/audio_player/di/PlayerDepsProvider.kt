package com.github.educationissimple.audio_player.di

import kotlin.properties.Delegates.notNull

interface PlayerDepsProvider {

    val deps: PlayerDeps

    companion object : PlayerDepsProvider by PlayerDepsStore

}

object PlayerDepsStore : PlayerDepsProvider {
    override var deps: PlayerDeps by notNull()
}
