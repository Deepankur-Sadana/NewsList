package com.example.newslist.presentation.news.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newslist.domain.entity.News
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.newslist.domain.entity.Response
import com.example.newslist.domain.usecase.GetNews
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNews: GetNews,
) : ViewModel() {

    val newsResponse: StateFlow<Response<News>> =
        getNews(savedStateHandle.get<Int>("id")!!).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Response.Loading(),
        )
}
