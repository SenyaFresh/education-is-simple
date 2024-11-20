package com.github.educationissimple.news.domain.repositories

import androidx.paging.PagingData
import com.github.educationissimple.news.domain.entities.NewsEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the contract for a repository responsible for managing news data.
 */
interface NewsRepository {

    /**
     * Retrieves a flow of [PagingData] of [NewsEntity] representing news articles.
     *
     * @return A [Flow] emitting [PagingData] of [NewsEntity].
     */
    suspend fun getNews(): Flow<PagingData<NewsEntity>>

}