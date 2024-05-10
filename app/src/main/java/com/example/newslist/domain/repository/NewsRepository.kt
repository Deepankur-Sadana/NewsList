package com.example.newslist.domain.repository

import androidx.paging.PagingData
import com.example.newslist.domain.entity.News
import com.example.newslist.domain.entity.Response
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsList(): Flow<PagingData<News>>
    fun getNews(id: Int): Flow<Response<News>>
}
