package com.github.educationissimple.news.di

import com.github.educationissimple.common.di.Feature
import com.github.educationissimple.news.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides dependencies related to news functionality.
 */
@Module
class NewsModule {

    /**
     * Provides an instance of [NewsRepository] for news-related data operations.
     *
     * @param deps The [NewsDeps] interface, which contains the required news dependencies.
     * @return An instance of [NewsRepository].
     **/
    @Provides
    @Feature
    fun provideNewsRepository(deps: NewsDeps): NewsRepository = deps.newsRepository

}