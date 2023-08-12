package io.jadu.kibo.data.remote.repository

import io.jadu.kibo.data.remote.api.NewsApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApiService: NewsApiService) {

    suspend fun getNews(query: String, apiKey: String) = newsApiService.getNews(query, apiKey)

}