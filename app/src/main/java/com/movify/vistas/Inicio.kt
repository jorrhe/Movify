package com.movify.vistas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.movify.componentes.SinInternet
import info.movito.themoviedbapi.model.MovieDb

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Inicio(
    peliculas:List<MovieDb>,
    cargarSiguientePagina:()->Unit,
    cargarPelicula:(MovieDb)->Unit,

    error:Boolean,
    volverACargarPeliculas:()->Unit
) {

    if(error){
        SinInternet(
            reintentar = volverACargarPeliculas
        )
    }else{
        ListaPeliculas(
            peliculas = peliculas,
            cargarSiguientePagina = cargarSiguientePagina,
            cargarPelicula = cargarPelicula
        )
    }


}