package com.github.educationissimple.news.di

import com.github.educationissimple.common.di.Feature
import com.github.educationissimple.news.domain.repositories.NewsRepository
import dagger.BindsInstance
import dagger.Component

@Feature
@Component(modules = [NewsModule::class])
internal interface NewsComponent {

    fun inject(it: NewsDiContainer)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun deps(deps: NewsDeps): Builder

        fun build(): NewsComponent
    }

}

interface NewsDeps {
    val newsRepository: NewsRepository
}