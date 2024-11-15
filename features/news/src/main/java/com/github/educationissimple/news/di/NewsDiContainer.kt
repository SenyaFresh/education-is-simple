package com.github.educationissimple.news.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.github.educationissimple.news.presentation.viewmodels.NewsViewModel
import javax.inject.Inject

@Stable
class NewsDiContainer {
    @Inject
    lateinit var viewModelFactory: NewsViewModel.Factory
}

@Composable
fun rememberNewsDiContainer() : NewsDiContainer {
    return remember {
        NewsDiContainer().also {
            DaggerNewsComponent.builder().deps(NewsDepsProvider.deps).build().inject(it)
        }
    }
}