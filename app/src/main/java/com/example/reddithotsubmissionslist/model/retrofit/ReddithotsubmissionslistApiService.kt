package com.example.reddithotsubmissionslist.model.retrofit

import com.example.reddithotsubmissionslist.model.data.AllAwardings
import retrofit2.Call
import retrofit2.http.GET

interface ReddithotsubmissionslistApiService {
    @GET("hot")
    fun getRedditList(): Call<AllAwardings>
}