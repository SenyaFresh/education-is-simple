package com.github.educationissimple.data.news.repositories

import androidx.paging.PagingData
import com.github.educationissimple.data.news.entities.Article
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides news articles.
 */
interface NewsDataRepository {

    /**
     * Fetches a stream of paginated news articles.
     *
     * @return A [Flow] of [PagingData] representing a paginated stream of [Article] objects .
     */
    suspend fun getNews(): Flow<PagingData<Article>>

}