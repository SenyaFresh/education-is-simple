package com.github.educationissimple.data.news.entities

/**
 * Represents an article with detailed information such as content, publication details, and source.
 *
 * @property content The main content of the article as a. Can be `null` if not available.
 * @property publishedAt The publication date and time of the article. Can be `null` if not available.
 * @property source The source of the article represented by a [Source] object. Can be `null` if not available.
 * @property title The title of the article. Can be `null` if not available.
 * @property url The web URL of the article. Can be `null` if not available.
 * @property urlToImage The URL pointing to an image associated with the article. Can be `null` if not available.
 */
data class Article(
    val content: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)