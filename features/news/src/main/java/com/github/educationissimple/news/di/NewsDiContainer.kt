package com.github.educationissimple.news.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.github.educationissimple.news.presentation.viewmodels.NewsViewModel
import javax.inject.Inject

/**
 * A container for dependencies related to news functionality.
 *
 * This class holds the required dependencies, such as the [NewsViewModel.Factory],
 * which are injected using Dagger. It serves as a central point for managing news-related
 * dependencies in the composable functions.
 */
@Stable
class NewsDiContainer {

    /**
     * The factory for creating instances of [NewsViewModel].
     */
    @Inject
    lateinit var viewModelFactory: NewsViewModel.Factory

}

/**
 * A composable function that remembers and provides an instance of [NewsDiContainer].
 *
 * This function ensures that the [NewsDiContainer] is created once and injected with the
 * necessary dependencies using Dagger. It is useful for managing and providing news-related
 * dependencies in composables.
 *
 * @return The [NewsDiContainer] instance with injected dependencies.
 **/
@Composable
fun rememberNewsDiContainer() : NewsDiContainer {
    return remember {
        NewsDiContainer().also {
            DaggerNewsComponent.builder().deps(NewsDepsProvider.deps).build().inject(it)
        }
    }
}