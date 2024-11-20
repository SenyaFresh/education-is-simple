package com.github.educationissimple.glue.news.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.github.educationissimple.data.news.repositories.NewsDataRepository
import com.github.educationissimple.glue.news.mappers.toNewsEntity
import com.github.educationissimple.news.domain.entities.NewsEntity
import com.github.educationissimple.news.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Adapter class that implements the [NewsRepository] interface and serves as an intermediary
 * between the data source and the rest of the application.
 *
 * This class is responsible for transforming the data retrieved from the [NewsDataRepository]
 * into a form that is expected by the business logic (in this case, a list of [NewsEntity] objects).
 *
 * @param newsDataRepository The underlying repository that handles the data source logic.
 */
class AdapterNewsRepository @Inject constructor(
    private val newsDataRepository: NewsDataRepository,
) : NewsRepository {

    /**
     * Fetches a stream of paginated news articles.
     *
     * @return A [Flow] of [PagingData] representing a paginated stream of [NewsEntity] objects .
     */
    override suspend fun getNews(): Flow<PagingData<NewsEntity>> {
        return newsDataRepository.getNews()
            .map { pagingData -> pagingData.map { it.toNewsEntity() } }
    }

}