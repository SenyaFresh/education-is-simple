package com.github.educationissimple.news.domain.repositories

import androidx.paging.PagingData
import com.github.educationissimple.news.domain.entities.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(): Flow<PagingData<NewsEntity>>

}