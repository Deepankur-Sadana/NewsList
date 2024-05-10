package com.example.newslist.domain.usecase

import com.example.newslist.domain.entity.News
import com.example.newslist.domain.entity.Response
import com.example.newslist.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetNews @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(id: Int): Flow<Response<News>> {
        return newsRepository.getNews(id).flowOn(Dispatchers.IO)
    }
}
