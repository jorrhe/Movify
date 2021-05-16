package com.movify.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movify.database.ListWithMovies
import com.movify.database.Movie
import com.movify.database.MovieList
import com.movify.repositorios.ListRepository
import com.movify.repositorios.PeliculaRepositorio
import info.movito.themoviedbapi.model.MovieDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailedListViewModel(var repositorioListas: ListRepository, var repositorioPeliculas: PeliculaRepositorio): ViewModel() {
    var listInfo : MovieList = MovieList(-1, "")
    var actualList : List<MovieDb> by mutableStateOf(listOf())
        private set

    fun loadList(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            val listWithMovies = repositorioListas.getListById(id)

            if (listWithMovies != null) {
                actualList = listOf<MovieDb>()
                for(movie in listWithMovies.movies){
                    var dbMovie = repositorioPeliculas.getMovieById(movie.imdbId)
                    actualList = actualList + listOf(dbMovie)
                }
            }
        }
    }

}