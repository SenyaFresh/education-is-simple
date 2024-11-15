package com.github.educationissimple.data.news.services

import com.github.educationissimple.data.news.BuildConfig
import com.github.educationissimple.data.news.entities.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNewsApiService: NewsApiService {

    @GET("top-headlines?country=ru&category=science&apiKey=${BuildConfig.NEWS_API_KEY}&pageSize=20")
    override suspend fun getNews(@Query("page") page: Int): NewsResponse

}