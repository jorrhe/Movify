package com.movify.vistas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.movify.utils.getUrlCaratula
import com.movify.utils.isScrolledToTheEnd
import info.movito.themoviedbapi.model.MovieDb

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Inicio(
    peliculas:List<MovieDb>,
    cargarSiguientePagina:()->Unit,
    cargarPelicula:(MovieDb)->Unit
){

    val listState = rememberLazyListState()

    LazyVerticalGrid(
        cells = GridCells.Adaptive(120.dp),
        modifier = Modifier.wrapContentHeight(),
        state = listState
    ) {

        if(listState.isScrolledToTheEnd()){
            cargarSiguientePagina()
        }

        items(items = peliculas){pelicula->

            Image(
                painter = rememberCoilPainter(
                    request = getUrlCaratula(pelicula),
                    fadeIn = true
                ),
                contentDescription = pelicula.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .clickable { cargarPelicula(pelicula) }
            )

        }

    }


}

@Composable
@Preview("Inicio")
fun PreviewInicio(){
    val peliculas = listOf(MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb())

    peliculas.forEachIndexed {i,p ->
        p.title = "Pelicula $i"
        p.posterPath = "/bnuC6hu7AB5dYW26A3o6NNLlIlE.jpg"
    }

    Inicio(peliculas,{},{})
}