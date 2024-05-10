package com.example.newslist.domain.usecase

import androidx.paging.PagingData
import com.example.newslist.domain.entity.News
import com.example.newslist.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetNewsList @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<PagingData<News>> {
        return newsRepository.getNewsList()
            .flowOn(Dispatchers.IO)
    }
}
