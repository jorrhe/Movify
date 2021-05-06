package com.movify.vistas

import androidx.compose.foundation.Image
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.movify.utils.getUrlBackdrop
import info.movito.themoviedbapi.model.MovieDb
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.imageloading.isFinalState
import com.movify.R
import com.movify.utils.getUrlCaratula

@Composable
fun InfoPelicula(pelicula: MovieDb){

    Column {
        
        HeaderCaratula(pelicula)

        DatosPelicula(pelicula)
        
    }
}

@Composable
fun HeaderCaratula(pelicula: MovieDb){

    val painter = rememberCoilPainter(
        request = getUrlBackdrop(pelicula),
        fadeIn = true
    )

    Box{
        Image(
            painter = if (pelicula.backdropPath.isNullOrBlank()) {
                painterResource(id = R.drawable.backdrop_placeholder_blue)
            } else {
                painter
            },
            contentDescription = pelicula.title,
            modifier = Modifier
                .aspectRatio(1.7f)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )

        if(painter.loadState.isFinalState()) {

            Spacer(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black),
                            0f,
                            200f
                        )
                    )
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
            )


            Text(
                text = pelicula.title,
                style = MaterialTheme.typography.h5,
                color = Color.White,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.BottomCenter)
            )

        }

    }
}

@Composable
fun DatosPelicula(pelicula: MovieDb){

    Column(
        Modifier.padding(16.dp,0.dp)
    ){

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ){

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = ""
                )
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = ""
                )
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Filled.List,
                    contentDescription = ""
                )
            }

        }

        Text(
            text = if (pelicula.overview.isNullOrBlank()) {
                stringResource(id = R.string.description_not_found)
            }
            else {
                pelicula.overview
            },
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.body2,
        )

    }



}
