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

class SearchViewModel(val repository: PeliculaRepositorio) : ViewModel() {

    var foundMovies: List<MovieDb> by mutableStateOf(listOf())
        private set

    fun getMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val foundList = repository.getSearch(query)
            foundMovies = foundList
        }
    }

    fun cleanSearch() {
        foundMovies = listOf()
    }
}