package com.example.newslist.presentation.news.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.newslist.domain.entity.News

@Composable
fun NewsListScreen(
    navigateToDetail: (Int) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val viewModel = hiltViewModel<NewsListViewModel>()
    val newsPagingItems = viewModel.newsPagingDataFlow.collectAsLazyPagingItems()

    if (newsPagingItems.loadState.refresh is LoadState.Error) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                (newsPagingItems.loadState.refresh as LoadState.Error).error.message.orEmpty()
            )
        }
    }

    NewsListContent(
        newsPagingItems = newsPagingItems,
        navigateToDetail = navigateToDetail,
    )
}

@Composable
fun NewsListContent(
    newsPagingItems: LazyPagingItems<News>,
    navigateToDetail: (Int) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (newsPagingItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(
                    count = newsPagingItems.itemCount,
                    key = newsPagingItems.itemKey { it.id },
                ) { index ->
                    newsPagingItems[index]?.let { news ->
                        NewsItem(
                            news,
                            onClick = {
                                navigateToDetail(news.id)
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Divider(
                            color = MaterialTheme.colorScheme.secondary,
                            thickness = 0.2.dp,
                            modifier = Modifier.padding(horizontal = 20.dp),
                        )
                    }
                }
                item {
                    if (newsPagingItems.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
