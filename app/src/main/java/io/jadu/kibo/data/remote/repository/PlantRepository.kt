package io.jadu.kibo.data.remote.repository

import io.jadu.kibo.data.remote.api.PlantApiService
import javax.inject.Inject

class PlantRepository @Inject constructor(private val plantApiService: PlantApiService) {
    suspend fun getPlants(page: Int, apiKey: String) = plantApiService.getPlants(page, apiKey)

}