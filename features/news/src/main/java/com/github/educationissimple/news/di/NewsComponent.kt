package com.github.educationissimple.news.di

import com.github.educationissimple.common.di.Feature
import com.github.educationissimple.news.domain.repositories.NewsRepository
import dagger.BindsInstance
import dagger.Component

/**
 * Dagger component responsible for providing dependencies for the news feature.
 */
@Feature
@Component(modules = [NewsModule::class])
internal interface NewsComponent {

    fun inject(it: NewsDiContainer)

    /**
     * Builder interface for creating instances of [NewsComponent].
     */
    @Component.Builder
    interface Builder {
        /**
         * Binds the external dependencies for news into the component.
         *
         * @param deps The [NewsDeps] providing the required dependencies.
         * @return The builder instance for further configuration.
         **/
        @BindsInstance
        fun deps(deps: NewsDeps): Builder

        fun build(): NewsComponent
    }

}

/**
 * Interface defining the external dependencies required for news functionality.
 *
 * The dependencies include [NewsRepository] for data management.
 */
interface NewsDeps {
    val newsRepository: NewsRepository
}