package com.github.educationissimple.data.news.sources

import androidx.paging.PagingState
import com.github.educationissimple.data.news.entities.Article
import com.github.educationissimple.data.news.services.NewsApiService
import javax.inject.Inject

class NetworkNewsPagingSource @Inject constructor(
    private val newsApiService: NewsApiService,
): NewsPagingSource() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

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