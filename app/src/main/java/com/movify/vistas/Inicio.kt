package com.movify.vistas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import info.movito.themoviedbapi.model.MovieDb

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Inicio(
    peliculas:List<MovieDb>,
    cargarSiguientePagina:()->Unit,
    cargarPelicula:(MovieDb)->Unit
) {
    ListaPeliculas(
        peliculas = peliculas,
        cargarSiguientePagina = cargarSiguientePagina,
        cargarPelicula = cargarPelicula
    )
}