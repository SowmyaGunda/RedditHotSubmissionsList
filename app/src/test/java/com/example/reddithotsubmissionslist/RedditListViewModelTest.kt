package com.example.reddithotsubmissionslist


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.reddithotsubmissionslist.model.retrofit.ReddithotsubmissionslistApiService
import com.example.reddithotsubmissionslist.viewmodel.RedditListViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RedditListViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: ReddithotsubmissionslistApiService


    lateinit var redditListViewModel: RedditListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        redditListViewModel = RedditListViewModel(apiService)
    }

    @Test
    fun verifyApiInvoking() {
        redditListViewModel.fetchRedditListFromRepository()
        verify(apiService).getRedditList()
    }

}