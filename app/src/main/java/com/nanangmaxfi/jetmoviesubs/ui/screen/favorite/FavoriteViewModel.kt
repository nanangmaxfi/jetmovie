package com.nanangmaxfi.jetmoviesubs.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanangmaxfi.jetmoviesubs.data.MovieRepository
import com.nanangmaxfi.jetmoviesubs.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteState>>
        get() = _uiState

    fun getAllFavoriteMovies(){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAllFavoriteMovies()
                .collect{ movie ->
                    _uiState.value = UiState.Success(FavoriteState(movie))
                }
        }
    }

}