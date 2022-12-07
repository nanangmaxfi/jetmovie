package com.nanangmaxfi.jetmoviesubs.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nanangmaxfi.jetmoviesubs.ui.theme.JetMovieSubsTheme
import com.nanangmaxfi.jetmoviesubs.ui.theme.Red

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    if (isFavorite){
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Red)
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.White
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text = "Hapus Favorite",
                modifier = Modifier.align(Alignment.CenterVertically),
                color = Color.White
            )
        }
    }
    else{
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Icon(
                Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text = "Tambah Favorite",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FavoriteButtonPreview() {
    JetMovieSubsTheme {
        FavoriteButton(
            isFavorite = true,
            onClick = {}
        )
    }
}