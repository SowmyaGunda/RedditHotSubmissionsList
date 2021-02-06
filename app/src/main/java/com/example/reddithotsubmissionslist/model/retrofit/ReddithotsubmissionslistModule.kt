package com.example.reddithotsubmissionslist.model.retrofit

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class ReddithotsubmissionslistModule {
    companion object {
        const val DEFAULT_API_URL = "https://www.reddit.com/r/Android/"
        const val TIMEOUT: Long = 3000 // ms

    }

    @Singleton
    @Provides
    open fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()

    }

    @Singleton
    @Provides
    open fun provideGSON(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    open fun provideRetrofit(
            gsonConverterFactory: GsonConverterFactory,
            okHttpClient: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
                .baseUrl(DEFAULT_API_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build()
    }

}