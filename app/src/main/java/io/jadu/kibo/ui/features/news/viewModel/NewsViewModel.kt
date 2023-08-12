package io.jadu.kibo.ui.features.news.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.jadu.kibo.data.remote.models.NewsDto
import io.jadu.kibo.data.remote.repository.NewsRepository
import io.jadu.kibo.uitlities.Constants
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository):ViewModel() {

    private val _newsLiveData = MutableLiveData<NewsDto?>()
    val newsLiveData: MutableLiveData<NewsDto?>
        get() = _newsLiveData

    init {
        getNews("environment", Constants.NEWS_API_KEY)
    }

    private fun getNews(query: String, apiKey: String) = viewModelScope.launch {
        val response = newsRepository.getNews(query, apiKey)
        if(response.isSuccessful){
            _newsLiveData.postValue(response.body())
        }else{
            _newsLiveData.postValue(null)
        }
    }

}