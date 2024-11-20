package com.github.educationissimple.audio.di

import com.github.educationissimple.audio.domain.handlers.AudioListHandler
import com.github.educationissimple.audio.domain.repositories.AudioRepository
import com.github.educationissimple.common.di.Feature
import dagger.BindsInstance
import dagger.Component

/**
 * Dagger component responsible for providing dependencies for the audio feature.
 */
@Feature
@Component(modules = [AudioModule::class])
internal interface AudioComponent {

    fun inject(it: AudioDiContainer)


    /**
     * Builder interface for creating instances of [AudioComponent].
     *
     * Requires [AudioDeps] provided by [deps] function for providing external dependencies.
     */
    @Component.Builder
    interface Builder {
        /**
         * Binds the external dependencies for audio into the component.
         *
         * @param deps The [AudioDeps] providing the required dependencies.
         * @return The builder instance for further configuration.
         */
        @BindsInstance
        fun deps(deps: AudioDeps): Builder

        fun build(): AudioComponent
    }

}

/**
 * Interface defining the external dependencies required for audio functionality.
 *
 * The dependencies include [AudioRepository] for data management and [AudioListHandler]
 * for handling audio lists.
 */
interface AudioDeps {
    val audioRepository: AudioRepository
    val audioHandler: AudioListHandler
}