package com.stu71557.a71557_lab2

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Chair
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stu71557.a71557_lab2.models.Movie

@Composable
fun Screen2(
    movie: Movie, paddingValues: PaddingValues
) {
    LazyColumn(contentPadding = paddingValues
    ) {
        item {
            Screen2Content(movie = movie)
        }
    }
}

@Composable
private fun  Screen2Content(movie: Movie){
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Box {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                painter = painterResource(id = movie.posterResId),
                contentScale = ContentScale.Crop,
                alpha = 0.5f,
                contentDescription = "Movie Poster"
            )
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.Center)
                    .size(width = 200.dp, height = 300.dp),
                painter = painterResource(id = movie.posterResId),
                contentScale = ContentScale.Crop,
                contentDescription = "Movie Poster"
            )
        }

        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            TextWithLabel(
                modifier = Modifier.padding(top = 5.dp), label = "Starring:", text = movie.starring
            )
            TextWithLabel(label = "Running time:", text = movie.runningTime)

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = movie.description,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )

            SelectSeats(
                modifier = Modifier.padding(top = 15.dp), movie = movie
            )
        }

    }
}

@Composable
fun TextWithLabel(
    label: String, text: String, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun SelectSeats(
    movie: Movie, modifier: Modifier = Modifier
) {
    var selectedSeatNumbers by remember {
        mutableIntStateOf(0)
    }
    val buttonIncreaseNumberSeatsIsEnabled = selectedSeatNumbers < movie.maxSeats
    val buttonDecreaseNumberSeatsIsEnabled = selectedSeatNumbers > 0
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Text(
                text = "Select Seats",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(enabled = buttonDecreaseNumberSeatsIsEnabled, onClick = {

                    --selectedSeatNumbers

                }) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
                }

                Text(text = selectedSeatNumbers.toString())

                IconButton(enabled = buttonIncreaseNumberSeatsIsEnabled, onClick = {

                    ++selectedSeatNumbers
                }) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
                }
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(imageVector = Icons.Outlined.Chair, contentDescription = null)
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp), text = movie.maxSeats.toString()
                )
            }
        }
        Button(
            shape = RoundedCornerShape(8.dp),
            onClick = {
                Toast.makeText(context, "Get Tickets", Toast.LENGTH_SHORT).show()
            }) {
            Icon(imageVector = Icons.Outlined.ConfirmationNumber, contentDescription = null)
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Get Tickets")
        }
    }
}