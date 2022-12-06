package com.nanangmaxfi.jetmoviesubs.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nanangmaxfi.jetmoviesubs.data.MovieRepository
import com.nanangmaxfi.jetmoviesubs.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}