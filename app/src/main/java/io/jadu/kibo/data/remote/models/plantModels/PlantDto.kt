package io.jadu.kibo.data.remote.models.plantModels

data class PlantDto(
    val current_page: Int,
    val `data`: List<Data>,
    val from: Int,
    val last_page: Int,
    val per_page: Int,
    val to: Int,
    val total: Int
)