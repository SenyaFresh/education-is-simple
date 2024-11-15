package com.github.educationissimple.news.domain.usecases

import com.github.educationissimple.news.domain.repositories.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun getNews() = newsRepository.getNews()

}