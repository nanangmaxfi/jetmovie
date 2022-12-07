package com.nanangmaxfi.jetmoviesubs.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanangmaxfi.jetmoviesubs.data.MovieRepository
import com.nanangmaxfi.jetmoviesubs.model.FavoriteMovie
import com.nanangmaxfi.jetmoviesubs.model.Movie
import com.nanangmaxfi.jetmoviesubs.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteMovie>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteMovie>>
        get() = _uiState

    fun getMovieById(movieId: Long){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getMovieById(movieId))
        }
    }

    fun setFavoriteMovie(movie: Movie, isFavorite: Boolean){
        viewModelScope.launch {
            repository.updateFavoriteMovie(movie.id, isFavorite)
        }
    }
}