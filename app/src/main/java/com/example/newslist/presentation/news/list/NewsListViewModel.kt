package com.example.newslist.presentation.news.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newslist.domain.entity.News
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.newslist.domain.usecase.GetNewsList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsList: GetNewsList,
) : ViewModel() {

    val newsPagingDataFlow: Flow<PagingData<News>> = getNewsList()
        .cachedIn(viewModelScope)
}
