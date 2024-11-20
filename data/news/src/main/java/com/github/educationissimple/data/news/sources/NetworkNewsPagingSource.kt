package com.github.educationissimple.data.news.sources

import androidx.paging.PagingState
import com.github.educationissimple.data.news.entities.Article
import com.github.educationissimple.data.news.services.NewsApiService
import javax.inject.Inject

/**
 * A PagingSource implementation for loading news articles from a network API.
 *
 * This class fetches paginated data using the [NewsApiService] and uses
 * Paging 3 library's architecture for seamless data loading and caching.
 *
 * @param newsApiService The API service used to fetch news data.
 */
class NetworkNewsPagingSource @Inject constructor(
    private val newsApiService: NewsApiService,
): NewsPagingSource() {

    /**
     * Determines the key for refreshing the PagingSource.
     *
     * Calculates the refresh key based on the user's current scroll position.
     *
     * @param state The current state of the Paging system.
     * @return The page key to use for refreshing, or `null` if not available.
     */
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    /**
     * Loads a page of data from the network.
     *
     * @param params The parameters for the current load request, including the page key.
     * @return A 'LoadResult' containing the result of the request.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val response = newsApiService.getNews(page = page)

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.articles.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}