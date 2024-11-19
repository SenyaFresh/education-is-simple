package com.github.educationissimple.audio_player.di

import com.github.educationissimple.common.di.SingletonHolder

/**
 * Holder of [PlayerComponent] for audio-player module.
 */
object PlayerComponentHolder {

    /**
     * Singleton holder for [PlayerComponent].
     */
    private object Component : SingletonHolder<PlayerComponent>({
        DaggerPlayerComponent.builder()
            .deps(PlayerDepsProvider.deps)
            .build()
    })

    /**
     * Returns the instance of [PlayerComponent].
     */
    fun getInstance(): PlayerComponent {
        return Component.getInstance()
    }

}