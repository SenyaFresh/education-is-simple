package com.github.educationissimple.data.news.di

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.news.services.NewsApiService
import com.github.educationissimple.data.news.services.RetrofitNewsApiService
import dagger.Binds
import dagger.Module

@Module
interface NewsApiServiceModule {

    @Binds
    @AppScope
    fun bindNewsApiService(impl: RetrofitNewsApiService): NewsApiService

}