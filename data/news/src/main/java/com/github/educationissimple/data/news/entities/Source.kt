package com.github.educationissimple.data.news.entities

/**
 * Represents the source of an article, containing its unique identifier and name.
 *
 * @property id A unique identifier for the source.
 * @property name The display name of the source. Represents the source's recognizable name (e.g., "BBC News").
 */
data class Source(
    val id: String,
    val name: String
)