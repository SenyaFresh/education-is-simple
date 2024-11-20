package com.github.educationissimple.data.news.di

import com.github.educationissimple.common.di.AppScope
import com.github.educationissimple.common.di.BaseURL
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Dagger module that provides network-related dependencies.
 *
 */
@Module
class NetworkModule {

    /**
     * Provides a [Moshi] instance for JSON parsing.
     *
     * @return A configured Moshi instance.
     */
    @Provides
    @AppScope
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    /**
     * Provides an [OkHttpClient] instance for making network requests.
     *
     * The client includes interceptors for logging request and response bodies and headers.
     *
     * @return A configured [OkHttpClient] instance.
     */
    @Provides
    @AppScope
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .build()
    }

    /**
     * Provides the base URL for the [Retrofit] client.
     *
     * This URL serves as the root endpoint for API requests.
     *
     * @return The base URL as a string.
     */
    @Provides
    @AppScope
    @BaseURL
    fun provideBaseURL(): String {
        return "https://newsapi.org/v2/"
    }

    /**
     * Provides a [Retrofit] instance for creating API service interfaces.
     *
     * @param client The [OkHttpClient] used by [Retrofit].
     * @param moshi The Moshi instance for `JSON` parsing.
     * @param baseUrl The base URL for API requests.
     * @return A configured [Retrofit] instance.
     */
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