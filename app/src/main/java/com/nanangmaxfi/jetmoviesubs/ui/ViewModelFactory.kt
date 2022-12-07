package com.nanangmaxfi.jetmoviesubs.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nanangmaxfi.jetmoviesubs.data.MovieRepository
import com.nanangmaxfi.jetmoviesubs.ui.screen.detail.DetailMovieViewModel
import com.nanangmaxfi.jetmoviesubs.ui.screen.favorite.FavoriteViewModel
import com.nanangmaxfi.jetmoviesubs.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(repository) as T
        }
        else  if (modelClass.isAssignableFrom(DetailMovieViewModel::class.java)){
            return DetailMovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}