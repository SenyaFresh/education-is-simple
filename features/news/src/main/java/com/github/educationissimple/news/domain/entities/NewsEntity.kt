package com.github.educationissimple.news.domain.entities

/**
 * Represents a news article entity with details about its source, content, and publication metadata.
 *
 * @property source The name of the news source or publisher.
 * @property title The title of the news article.
 * @property content The main content of the news article.
 * @property imageUrl An optional URL for the article's associated image (can be null if no image is available).
 * @property publishedAt The publication date and time of the article, formatted as a string.
 * @property url The URL linking to the full article.
 */
data class NewsEntity(
    val source: String,
    val title: String,
    val content: String,
    val imageUrl: String?,
    val publishedAt: String,
    val url: String
)