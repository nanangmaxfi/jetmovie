package com.nanangmaxfi.jetmoviesubs.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nanangmaxfi.jetmoviesubs.di.Injection
import com.nanangmaxfi.jetmoviesubs.model.FavoriteMovie
import com.nanangmaxfi.jetmoviesubs.ui.ViewModelFactory
import com.nanangmaxfi.jetmoviesubs.ui.common.UiState
import com.nanangmaxfi.jetmoviesubs.ui.components.MovieItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllMovies()
            }
            is UiState.Success -> {
                HomeContent(
                    favoriteMovie = uiState.data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    favoriteMovie: List<FavoriteMovie>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ){
        items(favoriteMovie) { data ->
            MovieItem(
                image = data.movie.image,
                title = data.movie.title,
                modifier = Modifier.clickable {
                    navigateToDetail(data.movie.id)
                }
            )
        }
    }
}