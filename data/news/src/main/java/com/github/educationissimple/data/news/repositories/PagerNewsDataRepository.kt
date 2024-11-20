package com.github.educationissimple.data.news.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.educationissimple.data.news.entities.Article
import com.github.educationissimple.data.news.sources.NewsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of the [NewsDataRepository] interface that uses the Paging 3 library
 * to fetch and provide paginated news articles.
 *
 * @param newsPagingSource The PagingSource instance used for loading news data.
 */
class PagerNewsDataRepository @Inject constructor(
    private val newsPagingSource: NewsPagingSource,
) : NewsDataRepository {

    /**
     * Fetches a flow of paginated news articles.
     *
     * @return A [Flow] of [PagingData] representing a paginated stream of [Article] objects .
     */
    override suspend fun getNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { newsPagingSource }
        ).flow
    }

}