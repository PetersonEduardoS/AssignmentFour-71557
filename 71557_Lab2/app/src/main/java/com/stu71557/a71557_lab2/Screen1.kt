package com.stu71557.a71557_lab2

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stu71557.a71557_lab2.models.Movie

@Composable
fun Screen1(
    movies: List<Movie>,
    paddingValues: PaddingValues,
    onNavigateToSecondScreen: (Movie) -> Unit
){
    MovieList(movies = movies, contentPadding = paddingValues, onNavigateToSecondScreen = onNavigateToSecondScreen)
}

@Composable
private fun MovieList(
    movies: List<Movie>,
    contentPadding: PaddingValues,
    onNavigateToSecondScreen: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid (
        modifier = modifier.padding(8.dp),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        columns = GridCells.Fixed(3)
            ) {
        items(movies) { movie ->
            MovieItem(movie = movie, onClick = { onNavigateToSecondScreen(movie) })
        }
    }
}

@Composable
private fun MovieItem(
    movie: Movie, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .clickable { onClick() }
            .size(width = 140.dp, height = 300.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = movie.posterResId),
            contentScale = ContentScale.Crop,
            contentDescription = "Movie poster",
        )

        Text(
            text = movie.title, fontSize = 14.sp
        )
    }
}