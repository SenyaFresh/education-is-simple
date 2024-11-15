package com.github.educationissimple.data.news.di

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.news.services.NewsApiService
import com.github.educationissimple.data.news.sources.NetworkNewsPagingSource
import com.github.educationissimple.data.news.sources.NewsPagingSource
import dagger.Module
import dagger.Provides

@Module
class NewsPagingSourceModule {

    @Provides
    @AppScope
    fun provideNewsPagingSource(newsApiService: NewsApiService): NewsPagingSource {
        return NetworkNewsPagingSource(newsApiService)
    }

}