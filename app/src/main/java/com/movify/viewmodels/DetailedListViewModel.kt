package com.movify.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    var listInfo : MovieList by mutableStateOf(MovieList(-1,""))
    var actualList : List<MovieDb> by mutableStateOf(emptyList())
        private set

    fun loadList(id:Int){
        viewModelScope.launch(Dispatchers.IO){

            val listWithMovies = repositorioListas.getListById(id)

            if (listWithMovies != null) {

                listInfo = listWithMovies.movieList

                actualList = emptyList()

                for(movie in listWithMovies.movies){
                    val dbMovie = repositorioPeliculas.getMovieById(movie.imdbId)
                    actualList = actualList + listOf(dbMovie)
                }

            }
        }
    }

}

class DetailedListViewModelFactory(var repositorioListas: ListRepository, var repositorioPeliculas: PeliculaRepositorio): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailedListViewModel(repositorioListas,repositorioPeliculas) as T
    }
}