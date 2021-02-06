package com.example.reddithotsubmissionslist.di

import com.example.reddithotsubmissionslist.AppModule
import com.example.reddithotsubmissionslist.model.retrofit.ReddithotsubmissionslistModule
import com.example.reddithotsubmissionslist.viewmodel.RedditListViewModel
import com.example.reddithotsubmissionslist.viewmodel.ViewModelFactory
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ReddithotsubmissionslistModule::class])
interface ReddithotsubmissionslistDaggerComponent {
    fun inject(retroViewModel: RedditListViewModel)
    fun inject(viewModelFactory: ViewModelFactory)
}