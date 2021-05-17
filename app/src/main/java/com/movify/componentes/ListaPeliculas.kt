package com.movify.vistas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.movify.R
import com.movify.utils.getUrlCaratula
import com.movify.utils.isScrolledToTheEnd
import info.movito.themoviedbapi.model.MovieDb

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListaPeliculas(
    peliculas:List<MovieDb>,
    cargarSiguientePagina:()->Unit = {},
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

            CaratulaPelicula(pelicula,cargarPelicula)

        }

    }


}

@Composable
fun CaratulaPelicula(pelicula:MovieDb,cargarPelicula:(MovieDb)->Unit,modifier:Modifier = Modifier){
    Image(
        painter = if (pelicula.posterPath.isNullOrBlank()) {
            painterResource(id = R.drawable.poster_placeholder_blue)
        } else {
            rememberCoilPainter(
                request = getUrlCaratula(pelicula),
                fadeIn = true
            )
        },
        contentDescription = pelicula.title,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .height(200.dp)
            .clickable { cargarPelicula(pelicula) }
    )
}

@Composable
@Preview("ListaPeliculas")
fun PreviewListaPeliculas(){
    val peliculas = listOf(MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb())

    peliculas.forEachIndexed {i,p ->
        p.title = "Pelicula $i"
        p.posterPath = "/bnuC6hu7AB5dYW26A3o6NNLlIlE.jpg"
    }

    ListaPeliculas(peliculas,{},{})
}