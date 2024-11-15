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

class AdapterNewsRepository @Inject constructor(
    private val newsDataRepository: NewsDataRepository,
) : NewsRepository {

    override suspend fun getNews(): Flow<PagingData<NewsEntity>> {
        return newsDataRepository.getNews()
            .map { pagingData -> pagingData.map { it.toNewsEntity() } }
    }

}