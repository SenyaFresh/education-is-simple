package com.github.educationissimple.data.news.di

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.news.services.NewsApiService
import com.github.educationissimple.data.news.services.RetrofitNewsApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class NewsApiServiceModule {

    @Provides
    @AppScope
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(RetrofitNewsApiService::class.java)
    }

}