package com.github.educationissimple.data.news.di

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.common.di.BaseURL
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    @AppScope
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @AppScope
    @BaseURL
    fun provideBaseURL(): String {
        return "https://api.openai.com/v1/chat/"
    }

    @Provides
    @AppScope
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi, @BaseURL baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

}