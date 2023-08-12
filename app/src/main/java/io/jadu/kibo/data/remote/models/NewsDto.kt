package io.jadu.kibo.data.remote.models

data class NewsDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)