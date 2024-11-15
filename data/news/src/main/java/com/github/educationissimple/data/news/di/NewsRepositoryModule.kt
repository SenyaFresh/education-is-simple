package com.github.educationissimple.data.news.di

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.news.repositories.NewsRepository
import com.github.educationissimple.data.news.repositories.PagerNewsRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, NewsApiServiceModule::class, NewsPagingSourceModule::class])
interface NewsRepositoryModule {

    @Binds
    @AppScope
    fun bindNewsRepository(impl: PagerNewsRepository): NewsRepository

}