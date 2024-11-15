package com.github.educationissimple.glue.news.mappers

import com.github.educationissimple.data.news.entities.Article
import com.github.educationissimple.glue.news.utils.parseContent
import com.github.educationissimple.news.domain.entities.NewsEntity

fun Article.toNewsEntity(): NewsEntity {

    return NewsEntity(
        source = source?.name ?: "Неизвестный источник",
        title = title ?: "Заголовок не найден",
        url = url ?: "Ссылка на статью не найдена",
        imageUrl = urlToImage,
        publishedAt = publishedAt?.split("T")?.first() ?: "",
        content = content?.let { parseContent(it) } ?: "Контент не найден"
    )

}