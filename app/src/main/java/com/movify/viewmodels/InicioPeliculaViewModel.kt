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
import java.lang.Exception

class InicioPeliculaViewModel(private val repositorio: PeliculaRepositorio) : ViewModel() {

    var peliculas:List<MovieDb> by mutableStateOf(listOf())
        private set

    var error:Boolean by mutableStateOf(false)

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
            try{
                val peliculasNuevas = repositorio.getUltimasPeliculas(pagina)
                peliculas = peliculas + peliculasNuevas
                pagina++
                cargando = false
                error = false
            }catch (e:Exception){
                cargando = false
                println(e)
                if(peliculas.isEmpty()){
                    error = true
                }
            }
        }
    }

}

class InicioPeliculaViewModelFactory(private val repositorio: PeliculaRepositorio): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InicioPeliculaViewModel(repositorio) as T
    }
}