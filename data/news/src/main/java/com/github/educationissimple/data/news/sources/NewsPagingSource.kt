package com.github.educationissimple.data.news.sources

import androidx.paging.PagingSource
import com.github.educationissimple.data.news.entities.Article

abstract class NewsPagingSource: PagingSource<Int, Article>()