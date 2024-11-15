package com.github.educationissimple.data.news.di

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.news.repositories.NewsDataRepository
import com.github.educationissimple.data.news.repositories.PagerNewsDataRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, NewsApiServiceModule::class, NewsPagingSourceModule::class])
interface NewsDataRepositoryModule {

    @Binds
    @AppScope
    fun bindNewsRepository(impl: PagerNewsDataRepository): NewsDataRepository

}