package com.example.nytimes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytimes.dao.NewsResponse
import com.example.nytimes.service.NewsApiService
import com.example.nytimes.service.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {
    private val apiService: NewsApiService = RetrofitInstance.newsApi

    private val _articleResults = MutableLiveData<NewsResponse>()
    val articleResults: LiveData<NewsResponse> get() = _articleResults

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun popularArticles(section: String, apiKey: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                apiService.getMostPopularNews(section, apiKey).enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                        if (response.isSuccessful) {
                            _articleResults.postValue(response.body())
                        } else {
                            _errorMessage.postValue("Error: ${response.code()} ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        _errorMessage.postValue("Failure: ${t.message}")
                    }
                })
            }
        }
    }

    fun searchArticles(query: String, apiKey: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                apiService.searchArticles(query, apiKey).enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                        if (response.isSuccessful) {
                            _articleResults.postValue(response.body())
                        } else {
                            _errorMessage.postValue("Error: ${response.code()} ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        _errorMessage.postValue("Failure: ${t.message}")
                    }
                })
            }
        }
    }
}