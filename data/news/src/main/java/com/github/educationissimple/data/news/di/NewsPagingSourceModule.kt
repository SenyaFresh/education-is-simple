package com.github.educationissimple.data.news.di

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.news.services.NewsApiService
import com.github.educationissimple.data.news.sources.NetworkNewsPagingSource
import com.github.educationissimple.data.news.sources.NewsPagingSource
import dagger.Module
import dagger.Provides

/**
 * Dagger module that provides a [NewsPagingSource].
 */
@Module
class NewsPagingSourceModule {

    /**
     * Provides a [NewsPagingSource] implementation using the provided [NewsApiService].
     *
     * @param newsApiService The API service used by the [NewsPagingSource] to fetch data.
     * @return A [NewsPagingSource] instance.
     */
    @Provides
    @AppScope
    fun provideNewsPagingSource(newsApiService: NewsApiService): NewsPagingSource {
        return NetworkNewsPagingSource(newsApiService)
    }

}