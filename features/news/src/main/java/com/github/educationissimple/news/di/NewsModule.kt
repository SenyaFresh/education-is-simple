package com.github.educationissimple.news.di

import com.github.educationissimple.common.di.Feature
import com.github.educationissimple.news.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class NewsModule {

    @Provides
    @Feature
    fun provideNewsRepository(deps: NewsDeps): NewsRepository = deps.newsRepository

}