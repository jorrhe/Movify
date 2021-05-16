package com.movify.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.movify.repositorios.PeliculaRepositorio
import info.movito.themoviedbapi.model.MovieDb

class InfoPeliculaViewModel(val repositorio: PeliculaRepositorio) : ViewModel() {

    var pelicula:MovieDb? by mutableStateOf(null)

    /*todo https://developers.themoviedb.org/3/movies/get-movie-watch-providers
      todo Pasarle el repo de listas y añadir funcion para añadir y quitar de las listas desde aquí
    *  */

}