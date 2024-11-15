package com.github.educationissimple.news.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.educationissimple.news.domain.entities.NewsEntity
import com.github.educationissimple.news.domain.usecases.GetNewsUseCase
import com.github.educationissimple.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
): BaseViewModel() {

    private val _news = MutableStateFlow<PagingData<NewsEntity>>(PagingData.empty())
    val news = _news.asStateFlow()

    init {
        collectNews()
    }

    private fun collectNews() = viewModelScope.launch {
        getNewsUseCase.getNews().distinctUntilChanged().cachedIn(viewModelScope).collect {
            _news.value = it
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getNewsUseCase: GetNewsUseCase
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == NewsViewModel::class.java)
            return NewsViewModel(
                getNewsUseCase
            ) as T
        }
    }
}