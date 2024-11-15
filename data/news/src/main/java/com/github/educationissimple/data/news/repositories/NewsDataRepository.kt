package com.github.educationissimple.data.news.repositories

import androidx.paging.PagingData
import com.github.educationissimple.data.news.entities.Article
import kotlinx.coroutines.flow.Flow

interface NewsDataRepository {

    suspend fun getNews(): Flow<PagingData<Article>>

}