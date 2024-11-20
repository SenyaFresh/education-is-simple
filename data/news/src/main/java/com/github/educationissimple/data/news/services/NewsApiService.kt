package com.github.educationissimple.data.news.services

import com.github.educationissimple.data.news.entities.NewsResponse

/**
 * Service that communicates with the news API.
 */
interface NewsApiService {

    /**
     * Fetches a page of news articles from the news API.
     *
     * @param page The page number to fetch, starting from 1.
     * @return A [NewsResponse] containing a list of articles, request status,
     * and the total number of results.
     */
    suspend fun getNews(page: Int): NewsResponse

}