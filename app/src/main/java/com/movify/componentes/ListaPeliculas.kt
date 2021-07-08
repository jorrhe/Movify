package com.movify.vistas

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.movify.R
import com.movify.ui.theme.GradienteMovify
import com.movify.ui.theme.VerdeMovify
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

    if (pelicula.posterPath.isNullOrBlank()) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .height(200.dp)
                .background(GradienteMovify)
                .clickable { cargarPelicula(pelicula) }

        ) {

            Text(
                text = pelicula.title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

    }else{

        Image(
            painter =  rememberCoilPainter(
                request = getUrlCaratula(pelicula),
                fadeIn = true
            ),
            contentDescription = pelicula.title,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .height(200.dp)
                .clickable { cargarPelicula(pelicula) }
        )
    }




}

@Composable
@Preview("ListaPeliculas")
fun PreviewListaPeliculas(){
    val peliculas = listOf(MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb(),MovieDb())

    peliculas.forEachIndexed {i,p ->
        p.title = "Pelicula $i"
        //p.posterPath = "/bnuC6hu7AB5dYW26A3o6NNLlIlE.jpg"
    }

    ListaPeliculas(peliculas,{},{})
}