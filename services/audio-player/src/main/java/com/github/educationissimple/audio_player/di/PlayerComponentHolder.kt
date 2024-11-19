package com.github.educationissimple.audio_player.di

import com.github.educationissimple.common.di.SingletonHolder

/**
 * Holder of dagger component for audio-player module.
 */
object PlayerComponentHolder {

    private object Component : SingletonHolder<PlayerComponent>({
        DaggerPlayerComponent.builder()
            .deps(PlayerDepsProvider.deps)
            .build()
    })

    fun getInstance(): PlayerComponent {
        return Component.getInstance()
    }

}