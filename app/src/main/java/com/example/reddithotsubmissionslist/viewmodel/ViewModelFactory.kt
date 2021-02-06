package com.example.reddithotsubmissionslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reddithotsubmissionslist.di.DaggerReddithotsubmissionslistDaggerComponent
import com.example.reddithotsubmissionslist.di.ReddithotsubmissionslistDaggerComponent
import com.example.reddithotsubmissionslist.model.retrofit.ReddithotsubmissionslistApiService
import com.example.reddithotsubmissionslist.model.retrofit.ReddithotsubmissionslistModule
import retrofit2.Retrofit
import javax.inject.Inject

class ViewModelFactory : ViewModelProvider.Factory {
    private lateinit var apiComponent: ReddithotsubmissionslistDaggerComponent
    @Inject
    lateinit var retrofit: Retrofit


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        initDaggerComponent()
        if (modelClass.isAssignableFrom(RedditListViewModel::class.java)) {
            return RedditListViewModel(fetchApiService()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    private fun fetchApiService(): ReddithotsubmissionslistApiService {
        return retrofit.create(ReddithotsubmissionslistApiService::class.java)
    }

    private fun initDaggerComponent() {
        apiComponent = DaggerReddithotsubmissionslistDaggerComponent
            .builder()
            .reddithotsubmissionslistModule(ReddithotsubmissionslistModule())
            .build()
        apiComponent.inject(this)
    }
}