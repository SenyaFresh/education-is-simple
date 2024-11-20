package com.github.educationissimple.data.news.services

import com.github.educationissimple.data.news.BuildConfig
import com.github.educationissimple.data.news.entities.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Implementation of the [NewsApiService] interface using 'Retrofit'.
 *
 * This interface defines the API endpoint for fetching news articles, specifically in the
 * "science" category, using Retrofit annotations for configuration.
 */
interface RetrofitNewsApiService: NewsApiService {

    @GET("top-headlines?category=science&apiKey=${BuildConfig.NEWS_API_KEY}&pageSize=20")
    override suspend fun getNews(@Query("page") page: Int): NewsResponse

}