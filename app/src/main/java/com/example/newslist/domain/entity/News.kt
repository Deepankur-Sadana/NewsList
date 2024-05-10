package com.example.newslist.domain.entity

data class News(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String> = listOf(),
)
