package com.movify.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movify.repositorios.PeliculaRepositorio
import info.movito.themoviedbapi.model.MovieDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoPeliculaViewModel(val repositorio: PeliculaRepositorio) : ViewModel() {

    var pelicula:MovieDb? by mutableStateOf(null)

    var peliculasRelacionadas:List<MovieDb> by mutableStateOf(emptyList())
        private set

    var imagenesServicios:List<String> by mutableStateOf(emptyList())
        private set

    //todo https://developers.themoviedb.org/3/movies/get-movie-watch-providers
    //todo Pasarle el repo de listas y añadir funcion para añadir y quitar de las listas desde aquí

    fun getPelicula(id:Int){

        viewModelScope.launch(Dispatchers.IO) {

            val peliculaDatosNuevos = repositorio.getPelicula(id)

            pelicula = peliculaDatosNuevos

            peliculasRelacionadas = peliculaDatosNuevos.similarMovies

            imagenesServicios = repositorio.getServicios(id)

        }
    }

    fun cambioPelicula(pelicula:MovieDb){
        this.pelicula = pelicula
        peliculasRelacionadas = emptyList()
        imagenesServicios = emptyList()
    }

}

class InfoPeliculaViewModelFactory(private val repositorio: PeliculaRepositorio): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InfoPeliculaViewModel(repositorio) as T
    }
}