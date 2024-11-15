package com.github.educationissimple.news.domain.entities

data class NewsEntity(
    val author: String,
    val title: String,
    val content: String,
    val imageUrl: String?,
    val publishedAt: String,
    val url: String
)