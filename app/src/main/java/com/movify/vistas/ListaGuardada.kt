package com.movify.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movify.database.InfoLista
import com.movify.database.Pelicula
import com.movify.ui.theme.MovifyTheme
import com.movify.utils.convertirListaAMovieDb
import info.movito.themoviedbapi.model.MovieDb

@Composable
fun ListaGuardada(listaPeliculas: List<Pelicula>, cargarPelicula:(MovieDb)->Unit){

    ListaPeliculas(peliculas = convertirListaAMovieDb(listaPeliculas), cargarPelicula = cargarPelicula)

}


@Preview
@Composable
fun PreviewListaGuardada(){

}