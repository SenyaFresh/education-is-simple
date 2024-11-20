package com.github.educationissimple.data.news.di

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.data.news.services.NewsApiService
import com.github.educationissimple.data.news.services.RetrofitNewsApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Dagger module that provides the [NewsApiService] implementation using [Retrofit].
 */
@Module
class NewsApiServiceModule {

    /**
     * Provides an instance of [NewsApiService] using [Retrofit].
     *
     * @param retrofit The Retrofit instance used to create the service.
     * @return A concrete implementation of the [NewsApiService].
     */
    @Provides
    @AppScope
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(RetrofitNewsApiService::class.java)
    }

}