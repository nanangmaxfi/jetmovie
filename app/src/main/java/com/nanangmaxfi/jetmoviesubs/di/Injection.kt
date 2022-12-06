package com.nanangmaxfi.jetmoviesubs.di

import com.nanangmaxfi.jetmoviesubs.data.MovieRepository

object Injection {
    fun provideRepository(): MovieRepository{
        return MovieRepository.getInstance()
    }
}