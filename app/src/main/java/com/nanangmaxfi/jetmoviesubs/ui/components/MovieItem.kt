package com.nanangmaxfi.jetmoviesubs.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.nanangmaxfi.jetmoviesubs.ui.theme.JetMovieSubsTheme
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieItem(
    image: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        GlideImage(
            imageModel = {"https://image.tmdb.org/t/p/w500/$image"},
            modifier = modifier,
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
            }
        )
      //  CoilImage(imageModel = {"https://image.tmdb.org/t/p/w500/$image"})
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MovieItemPreview() {
    JetMovieSubsTheme {
        MovieItem(image = "pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg", title = "Black Adam")
    }
}