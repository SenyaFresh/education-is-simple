package com.github.educationissimple.news.presentation.screens

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.educationissimple.common.Core
import com.github.educationissimple.news.R
import com.github.educationissimple.news.di.NewsDiContainer
import com.github.educationissimple.news.di.rememberNewsDiContainer
import com.github.educationissimple.news.domain.entities.NewsEntity
import com.github.educationissimple.news.presentation.components.NewsListItem
import com.github.educationissimple.news.presentation.viewmodels.NewsViewModel
import com.github.educationissimple.presentation.ErrorMessage
import com.github.educationissimple.presentation.locals.LocalSpacing

/**
 * A composable function that serves as the main screen for displaying a list of news articles.
 */
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
    val uriHandler = LocalUriHandler.current

    LazyColumn {
        items(count = news.itemCount) { index ->
            NewsListItem(
                news = news[index]!!,
                onMoreClick = {
                    val urlParsed = kotlin.runCatching { Uri.parse(news[index]!!.url) }
                    if (urlParsed.isSuccess && urlParsed.getOrNull() != null) {
                        val url = urlParsed.getOrNull()!!
                        uriHandler.openUri(url.toString())
                    } else {
                        Core.toaster.showToast("Ошибка. Ссылка недействительна.")
                    }
                }
            )
        }

        news.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillParentMaxHeight()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillParentMaxHeight()
                        ) {
                            ErrorMessage(
                                message = stringResource(R.string.loading_error),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = LocalSpacing.current.medium)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    item {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = LocalSpacing.current.medium)
                        ) {
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
}