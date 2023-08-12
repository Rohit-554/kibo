package io.jadu.kibo.data.remote.api

import io.jadu.kibo.data.remote.models.NewsDto
import io.jadu.kibo.uitlities.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String = "environment",
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ) : Response<NewsDto>
}