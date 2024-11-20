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

/**
 * ViewModel class responsible for managing the state and business logic of the News screen.
 * It uses a use case to fetch paginated news data and provides it as a state flow.
 */
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
): BaseViewModel() {

    /** State flow containing the paginated news data. **/
    private val _news = MutableStateFlow<PagingData<NewsEntity>>(PagingData.empty())
    val news = _news.asStateFlow()

    init {
        collectNews()
    }

    /**
     * Collects the news data using the provided use case and updates the state flow.
     */
    private fun collectNews() = viewModelScope.launch {
        getNewsUseCase.getNews().distinctUntilChanged().cachedIn(viewModelScope).collect {
            _news.value = it
        }
    }

    /**
     * Factory class for creating an instance of [NewsViewModel].
     */
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