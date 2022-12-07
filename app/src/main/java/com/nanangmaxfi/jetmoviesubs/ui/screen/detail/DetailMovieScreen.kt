package com.nanangmaxfi.jetmoviesubs.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nanangmaxfi.jetmoviesubs.di.Injection
import com.nanangmaxfi.jetmoviesubs.ui.ViewModelFactory
import com.nanangmaxfi.jetmoviesubs.ui.common.UiState
import com.nanangmaxfi.jetmoviesubs.ui.components.FavoriteButton
import com.nanangmaxfi.jetmoviesubs.ui.theme.JetMovieSubsTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailMovieScreen(
    movieId: Long,
    viewModel: DetailMovieViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getMovieById(movieId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    title = data.movie.title,
                    overview = data.movie.overview,
                    image = data.movie.image,
                    releaseDate = data.movie.releaseDate,
                    voteAverage = data.movie.voteAverage,
                    voteCount = data.movie.voteCount,
                    isFavorite = data.isFavorite,
                    onBackClick = navigateBack,
                    onUpdateFavorite = {
                        viewModel.setFavoriteMovie(data.movie, it)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    title: String,
    overview: String,
    image: String,
    releaseDate: String,
    voteAverage: Double,
    voteCount: Long,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onUpdateFavorite: (isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var favoriteState by rememberSaveable{ mutableStateOf(isFavorite) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                GlideImage(
                    imageModel = {"https://image.tmdb.org/t/p/w500/$image"},
                    // shows an indicator while loading an image.
                    loading = {
                        Box(modifier = Modifier.matchParentSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    },
                    // shows an error text if fail to load an image.
                    failure = {
                        Text(text = "image request failed.")
                    },
                    modifier = modifier.height(400.dp)
                        .fillMaxWidth()
                )
//                Image(
//                    painter = painterResource(R.drawable.ic_launcher_background),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = modifier
//                        .height(400.dp)
//                        .fillMaxWidth()
//                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = "$voteAverage / $voteCount",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colors.secondary
                )

                Text(
                    text = overview,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )
            }
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                FavoriteButton(
                    isFavorite = favoriteState,
                    onClick = {
                        favoriteState = !favoriteState
                        onUpdateFavorite(favoriteState)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    JetMovieSubsTheme {
        DetailContent(
            title = "Black Adam",
            overview = "Nearly 5,000 years after he was bestowed with the almighty powers of the Egyptian gods—and imprisoned just as quickly—Black Adam is freed from his earthly tomb, ready to unleash his unique form of justice on the modern world.",
            image = "pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg",
            releaseDate = "2022-10-19",
            voteAverage = 7.3,
            voteCount = 2496,
            isFavorite = false,
            onBackClick = {  },
            onUpdateFavorite = {}
        )
    }
}