package com.github.educationissimple.news.domain.usecases

import androidx.paging.PagingData
import com.github.educationissimple.news.domain.entities.NewsEntity
import com.github.educationissimple.news.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case responsible for retrieving news data.
 */
class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    /**
     * Retrieves a flow of [PagingData] of [NewsEntity] representing news articles.
     *
     * @return A [Flow] emitting [PagingData] of [NewsEntity].
     */
    suspend fun getNews() = newsRepository.getNews()

}