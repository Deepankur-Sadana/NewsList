package com.example.newslist.presentation.news.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.newslist.domain.entity.News
import com.example.newslist.domain.entity.Response

@Composable
fun NewsDetailScreen(snackbarHostState: SnackbarHostState) {
    val viewModel = hiltViewModel<NewsDetailViewModel>()
    val newsResponse by viewModel.newsResponse.collectAsStateWithLifecycle()

    if (newsResponse is Response.Error) {
        LaunchedEffect(key1 = snackbarHostState) {
            snackbarHostState.showSnackbar((newsResponse as Response.Error).error)
        }
    }

    NewsDetailContent(newsResponse = newsResponse)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewsDetailContent(newsResponse: Response<News>) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (newsResponse.data != null) {
            val news = newsResponse.data!!

            Row(modifier = Modifier.padding(20.dp)) {
                AsyncImage(
                    model = news.imageUrl,
                    modifier = Modifier.width(100.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        news.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        news.types.mapIndexed { index, type ->
                            Text(type)
                            if (index < news.types.lastIndex) Text("•")
                        }
                    }
                }
            }
        }
        if (newsResponse is Response.Loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}
