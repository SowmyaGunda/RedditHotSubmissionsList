package com.example.reddithotsubmissionslist.model.data


data class RedditData (

        val modhash : String,
        val dist : Int,
        val children : List<Child>,
        val after : String,
        val before : String
)