package com.github.educationissimple.data.news.services

import com.github.educationissimple.data.news.entities.NewsResponse

interface NewsApiService {

    suspend fun getNews(page: Int): NewsResponse

}