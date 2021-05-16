package com.movify.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.movify.database.ListWithMovies
import com.movify.database.Movie
import com.movify.database.MovieList
import com.movify.repositorios.ListRepository
import info.movito.themoviedbapi.model.MovieDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListsViewModel(val repositorio: ListRepository) : ViewModel() {
    //Si no va dividir en dos:: Info de Lista y Lista en sí
    var list: List<MovieList>? by mutableStateOf(listOf())
        private set

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val newList = repositorio.getAlLists()?:listOf()
            list = newList
        }
    }
}