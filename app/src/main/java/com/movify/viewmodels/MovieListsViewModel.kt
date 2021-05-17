package com.movify.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movify.database.InfoLista
import com.movify.repositorios.ListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListsViewModel(val repositorio: ListRepository) : ViewModel() {
    //Si no va dividir en dos:: Info de ListaGuardada y ListaGuardada en sí
    var list: List<InfoLista>? by mutableStateOf(listOf())
        private set

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val newList = repositorio.getAlLists()?:listOf()
            list = newList
        }
    }
}

class MovieListsViewModelFactory(private val repositorio: ListRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieListsViewModel(repositorio) as T
    }

}