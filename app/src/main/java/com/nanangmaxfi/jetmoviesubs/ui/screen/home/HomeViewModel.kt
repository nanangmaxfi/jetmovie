package com.nanangmaxfi.jetmoviesubs.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanangmaxfi.jetmoviesubs.data.MovieRepository
import com.nanangmaxfi.jetmoviesubs.model.FavoriteMovie
import com.nanangmaxfi.jetmoviesubs.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteMovie>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavoriteMovie>>>
        get() = _uiState

    fun getAllMovies() {
        viewModelScope.launch {
            repository.getAllMovies()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}