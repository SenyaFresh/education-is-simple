package com.github.educationissimple.data.news.entities

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
