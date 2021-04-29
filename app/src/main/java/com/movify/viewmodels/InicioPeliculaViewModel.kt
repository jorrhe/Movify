package com.movify.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movify.repositorios.PeliculaRepositorio
import info.movito.themoviedbapi.model.MovieDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InicioPeliculaViewModel(val repositorio: PeliculaRepositorio) : ViewModel() {

    var peliculas:List<MovieDb> by mutableStateOf(listOf())
        private set

    private var cargando = false

    var pagina = 1

    init{
        siguientePagina()
    }

    fun siguientePagina(){

        if(cargando)
            return

        cargando = true

        viewModelScope.launch(Dispatchers.IO) {
            val peliculasNuevas = repositorio.getUltimasPeliculas(pagina)
            peliculas = peliculas + peliculasNuevas
            pagina++
            cargando = false
        }
    }

}