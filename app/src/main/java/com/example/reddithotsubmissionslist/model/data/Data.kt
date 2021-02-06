package com.example.reddithotsubmissionslist.model.data
data class Data (

	val modhash : String,
	val dist : Int,
	val children : List<Children>,
	val after : String,
	val before : String
)