package io.jadu.kibo.modules

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.jadu.kibo.data.remote.api.NewsApiService
import io.jadu.kibo.data.remote.api.PlantApiService
import io.jadu.kibo.ui.features.plant.adapter.PlantAdapter
import io.jadu.kibo.uitlities.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    @Singleton
    fun provideApiService(): NewsApiService = createRetrofitInstance(Constants.BASE_URL).create(NewsApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitInstance(): PlantApiService = createRetrofitInstance(Constants.PLANT_BASE_URL).create(PlantApiService::class.java)
    private fun createRetrofitInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                .setLenient()
                .create()))
            .build()
    }
}