package io.jadu.kibo.ui.features.plant.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jadu.kibo.data.remote.models.plantModels.PlantDto
import io.jadu.kibo.data.remote.repository.PlantRepository
import io.jadu.kibo.uitlities.Constants
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantsInfoViewModel @Inject constructor(private val plantRepository: PlantRepository): ViewModel() {
    private val _plantsLiveData = MutableLiveData<PlantDto?>()
    val plantsLiveData: LiveData<PlantDto?>
        get() = _plantsLiveData

    init {
        getPlantInfo(1,Constants.PLANT_API_KEY)
    }
    fun getPlantInfo(page:Int,apiKey:String) = viewModelScope.launch{
        val response = plantRepository.getPlants(page,apiKey)
        if(response.isSuccessful) {
            _plantsLiveData.postValue(response.body())
        }else{
            _plantsLiveData.postValue(null)
        }
    }
}