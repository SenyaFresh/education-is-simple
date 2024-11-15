package com.github.educationissimple.news.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.composables.ErrorMessage
import com.github.educationissimple.news.R
import com.github.educationissimple.news.di.NewsDiContainer
import com.github.educationissimple.news.di.rememberNewsDiContainer
import com.github.educationissimple.news.domain.entities.NewsEntity
import com.github.educationissimple.news.presentation.components.NewsListItem
import com.github.educationissimple.news.presentation.viewmodels.NewsViewModel
import com.github.educationissimple.presentation.locals.LocalSpacing

@Composable
fun NewsScreen(
    diContainer: NewsDiContainer = rememberNewsDiContainer(),
    viewModel: NewsViewModel = viewModel(factory = diContainer.viewModelFactory)
) {
    NewsContent(viewModel.news.collectAsLazyPagingItems())
}

@Composable
fun NewsContent(
    news: LazyPagingItems<NewsEntity>
) {
    LazyColumn {
        items(count = news.itemCount, key = { index -> news[index]!!.url }) { index ->
            NewsListItem(
                news = news[index]!!,
                onMoreClick = { }
            )
        }

        news.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth().fillParentMaxHeight()
                        ) {
                            CircularProgressIndicator(color = Highlight.Darkest)
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        ErrorMessage(
                            message = stringResource(R.string.loading_error),
                            onClickRetry = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth().padding(vertical = LocalSpacing.current.small)
                        ) {
                            CircularProgressIndicator(color = Highlight.Darkest)
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    item {
                        ErrorMessage(
                            message = stringResource(R.string.loading_error),
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}