package com.github.educationissimple.audio.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.github.educationissimple.audio.presentation.viewmodels.AudioViewModel
import javax.inject.Inject

/**
 * A container for dependencies related to audio functionality.
 *
 * This class holds the required dependencies, such as the [AudioViewModel.Factory.Factory],
 * which are injected using Dagger. It serves as a central point for managing audio-related
 * dependencies in the composable functions.
 */
@Stable
class AudioDiContainer {

    /**
     * The factory for creating instances of [AudioViewModel].
     */
    @Inject
    lateinit var viewModelFactory: AudioViewModel.Factory.Factory
}

/**
 * A composable function that remembers and provides an instance of [AudioDiContainer].
 *
 * This function ensures that the [AudioDiContainer] is created once and injected with the
 * necessary dependencies using Dagger. It is useful for managing and providing audio-related
 * dependencies in composables.
 *
 * @return The [AudioDiContainer] instance with injected dependencies.
 */
@Composable
fun rememberAudioDiContainer(): AudioDiContainer {
    return remember {
        AudioDiContainer().also {
            DaggerAudioComponent.builder().deps(AudioDepsProvider.deps).build().inject(it)
        }
    }
}