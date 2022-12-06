package com.nanangmaxfi.jetmoviesubs.model

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val image: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Long
)
