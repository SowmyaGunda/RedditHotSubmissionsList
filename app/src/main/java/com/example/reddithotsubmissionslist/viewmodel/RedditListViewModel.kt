package com.example.reddithotsubmissionslist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reddithotsubmissionslist.model.data.AllAwardings
import com.example.reddithotsubmissionslist.model.retrofit.ReddithotsubmissionslistApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class RedditListViewModel(apiService: ReddithotsubmissionslistApiService) : ViewModel() {
    private val reddithotsubmissionslistApiService: ReddithotsubmissionslistApiService = apiService
    val redditListLiveData: MutableLiveData<RedditListResponse> = MutableLiveData()

    fun fetchRedditListFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            val redditListInfo: Call<AllAwardings> = reddithotsubmissionslistApiService.getRedditList()
            redditListInfo.enqueue(object : Callback<AllAwardings> {
                override fun onFailure(call: Call<AllAwardings>, t: Throwable) {
                    Log.d("RedditListViewModel", "Failed:::" + t.message)
                    redditListLiveData.postValue(RedditListResponse.Failure(t as Exception))
                }

                override fun onResponse(call: Call<AllAwardings>, response: Response<AllAwardings>) {
                    if (response.isSuccessful) {
                        redditListLiveData.postValue(RedditListResponse.Success(response.body()!!))


                    } else {
                        redditListLiveData.postValue(RedditListResponse.Failure(HttpException(response)))
                    }

                }
            })
        }
    }

    sealed class RedditListResponse {
        data class Success(val allAwardings: AllAwardings) : RedditListResponse()
        data class Failure(val exception: Exception) : RedditListResponse()
    }
}