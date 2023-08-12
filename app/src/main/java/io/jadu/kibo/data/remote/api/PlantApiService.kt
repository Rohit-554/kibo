package io.jadu.kibo.data.remote.api

import io.jadu.kibo.data.remote.models.plantModels.PlantDto
import io.jadu.kibo.uitlities.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlantApiService {
    @GET("species-list")
    suspend fun getPlants(
        @Query("page") page: Int = 1,
        @Query("key") key: String = Constants.PLANT_API_KEY,
    ) : Response<PlantDto>


}