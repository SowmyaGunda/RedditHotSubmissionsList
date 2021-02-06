package com.example.reddithotsubmissionslist

import dagger.Module
import dagger.Provides

@Module
class AppModule constructor(myRetroApplication: ReddithotsubmissionslistApplication){

    var myRetroApplication:ReddithotsubmissionslistApplication

    init {
        this.myRetroApplication = myRetroApplication
    }

    @Provides
    fun provideMyRetroApplication():ReddithotsubmissionslistApplication{
        return myRetroApplication
    }
}