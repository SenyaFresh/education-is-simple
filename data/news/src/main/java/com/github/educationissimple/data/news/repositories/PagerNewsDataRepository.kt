package com.github.educationissimple.data.news.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.educationissimple.data.news.entities.Article
import com.github.educationissimple.data.news.sources.NewsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagerNewsDataRepository @Inject constructor(
    private val newsPagingSource: NewsPagingSource,
) : NewsDataRepository {

    override suspend fun getNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { newsPagingSource }
        ).flow
    }

}