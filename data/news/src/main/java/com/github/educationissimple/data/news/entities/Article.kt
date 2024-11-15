package com.github.educationissimple.data.news.entities

data class Article(
    val content: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)