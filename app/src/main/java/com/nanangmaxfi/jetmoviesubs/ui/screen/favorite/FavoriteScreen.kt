package com.nanangmaxfi.jetmoviesubs.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nanangmaxfi.jetmoviesubs.di.Injection
import com.nanangmaxfi.jetmoviesubs.ui.ViewModelFactory
import com.nanangmaxfi.jetmoviesubs.ui.common.UiState
import com.nanangmaxfi.jetmoviesubs.ui.components.MovieItem

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState){
            is UiState.Loading -> {
                viewModel.getAllFavoriteMovies()
            }
            is UiState.Success -> {
                FavoriteContent(
                    state = uiState.data,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    state: FavoriteState,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.favoriteMovies.isEmpty()){
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("Tidak ada data favorite")
        }
    }
    else{
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
            ){
                items(state.favoriteMovies, key = { it.movie.id }){ data ->
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
    }

}