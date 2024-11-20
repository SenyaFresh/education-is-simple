package com.github.educationissimple.data.news.sources

import androidx.paging.PagingSource
import com.github.educationissimple.data.news.entities.Article

/**
 * A PagingSource for loading news articles from a network API.
 */
abstract class NewsPagingSource: PagingSource<Int, Article>()