package com.example.reddithotsubmissionslist

import android.app.Application
import android.content.Context
import com.example.reddithotsubmissionslist.di.DaggerReddithotsubmissionslistDaggerComponent
import com.example.reddithotsubmissionslist.di.ReddithotsubmissionslistDaggerComponent
import com.example.reddithotsubmissionslist.model.retrofit.ReddithotsubmissionslistModule

class ReddithotsubmissionslistApplication : Application(){
    companion object {
        var ctx: Context? = null
        lateinit var apiComponent:ReddithotsubmissionslistDaggerComponent
    }
    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        apiComponent = initDaggerComponent()

    }

    fun getDaggerComponent(): ReddithotsubmissionslistDaggerComponent {
        return apiComponent
    }

    fun initDaggerComponent():ReddithotsubmissionslistDaggerComponent{
        apiComponent =   DaggerReddithotsubmissionslistDaggerComponent
            .builder()
            .reddithotsubmissionslistModule(ReddithotsubmissionslistModule())
            .build()
        return  apiComponent

    }
}