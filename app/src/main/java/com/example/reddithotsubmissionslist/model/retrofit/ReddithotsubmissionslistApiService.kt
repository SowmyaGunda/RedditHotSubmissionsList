package com.example.reddithotsubmissionslist.model.retrofit

import com.example.reddithotsubmissionslist.model.data.RedditHotListData
import retrofit2.Call
import retrofit2.http.GET

interface ReddithotsubmissionslistApiService {
    @GET("hot.json")
    fun getRedditList(): Call<RedditHotListData>
}