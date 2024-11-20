package com.github.educationissimple.data.news.entities

/**
 * Represents the structure of a news response containing metadata and a list of articles.
 *
 * @property articles A list of [Article] objects representing the news articles included in the response.
 * @property status A status of the response. Common values include "ok" or "error".
 * @property totalResults The total number of articles matching the query.
 */
data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
