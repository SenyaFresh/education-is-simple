package com.github.educationissimple.data.news.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Source(
    val id: String,
    val name: String
)